package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.scraper.CardScraperModel;
import pl.torlop.mtg.model.scraper.DeckScraperModel;
import pl.torlop.mtg.model.scraper.TournamentScraperModel;
import pl.torlop.mtg.service.repository.TournamentRepositoryService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MTGOTournamentScraperService implements TournamentScraperService {
    private final TournamentRepositoryService tournamentRepositoryService;

    @Value("${scraper.url}")
    private String url;
    @Value("${scraper.baseURL}")
    private String baseURL;


    private final Pattern pattern = Pattern.compile("[(].*(st|nd|rd|th).*[)]");

    @Override
    public List<TournamentScraperModel> scrapeTournaments(LocalDateTime beginDate, LocalDateTime endDate) {
        try {
            Document document = Jsoup.connect(url).get();
            Element tournamentList = document.selectFirst("ul.decklists-list");

            if (tournamentList == null) {
                log.error("No tournament list found");
                return new ArrayList<>();
            }

            List<Element> filteredTournaments = tournamentList.children().stream()
                    .filter(element -> element.className().equals("decklists-item"))
                    .filter(element -> {
                        LocalDateTime tournamentDate = getTournamentDate(element);
                        String tournamentUrl = element.selectFirst("a.decklists-link").attr("href");
                        return tournamentDate.isAfter(beginDate) && tournamentDate.isBefore(endDate) &&
                                tournamentRepositoryService.getTournamentByUrl(tournamentUrl) == null;
                    }).toList();

            List<TournamentScraperModel> tournaments = new ArrayList<>();
            for (Element element : filteredTournaments) {
                try {
                    int millis = 5000 + (int) (Math.random() * 2000) - 1000;
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {
                }
                TournamentScraperModel tournament = scrapeTournament(element, beginDate, endDate);
                if (tournament != null) {
                    tournaments.add(tournament);
                }
            }

            log.info("Scraped {} tournaments", tournaments.size());
            return tournaments;
        } catch (IOException e) {
            log.error("Error while scraping tournaments", e);
            return new ArrayList<>();
        }
    }

    public TournamentScraperModel scrapeTournament(Element element, LocalDateTime beginDate, LocalDateTime endDate) {
        try {
            Element linkElement = element.selectFirst("a.decklists-link");
            if (linkElement == null) {
                return null;
            }
            String name = getTournamentName(element);
            LocalDateTime dateTime = getTournamentDate(element);
            String format = name.split(" ")[0];
            String url = linkElement.attr("href");
            TournamentScraperModel tournament = new TournamentScraperModel();
            tournament.setName(name);
            tournament.setUrl(url);
            tournament.setFormat(format);
            tournament.setDate(dateTime);
            List<DeckScraperModel> decks = scrapeDecks(url);
            tournament.setDecks(decks);
            tournament.setPlayers(decks.size());
            return tournament;
        } catch (Exception e) {
            log.error("Error while scraping tournament", e);
            return null;
        }
    }

    public LocalDateTime getTournamentDate(Element element) {
        Element linkElement = element.selectFirst("a.decklists-link");
        String date = linkElement.selectFirst("time.decklists-date").attr("datetime");
        return LocalDateTime.parse(date.substring(0, date.length() - 1));
    }

    public String getTournamentName(Element element) {
        Element linkElement = element.selectFirst("a.decklists-link");
        return linkElement.selectFirst("div.decklists-details").selectFirst("h3").text();
    }

    public List<DeckScraperModel> scrapeDecks(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        try  {
            driver.get(baseURL + url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("decklistDecks")));
            webElement.getSize();
            Document document = Jsoup.parse(driver.getPageSource());
            Element deckList = document.selectFirst("div#decklistDecks");

            if (deckList == null) {
                throw new RuntimeException("No deck list found");
            }
            List<DeckScraperModel> decks = new ArrayList<>();
            deckList.children().stream()
                    .filter(element -> element.className().equals("decklist"))
                    .forEach(element -> {
                        DeckScraperModel deck = scrapeDeck(element);
                        if (deck != null) {
                            decks.add(deck);
                        }
                    });
            return decks;
        } finally {
            driver.quit();
        }
    }

    public DeckScraperModel scrapeDeck(Element element) {
        try {
            String playerAndPlace = element.selectFirst("header.decklist-header")
                    .selectFirst("p.decklist-player").text();
            Integer place = null;
            Integer matchesWon = null;
            Integer matchesLost = null;
            Integer matchesDrawn = null;
            String player = "";
            if (playerAndPlace.lastIndexOf(")") == -1) {
                player = playerAndPlace;
            } else {
                String resultSubstring = playerAndPlace.substring(playerAndPlace.lastIndexOf("(") + 1, playerAndPlace.lastIndexOf(")"));
                if (pattern.matcher(playerAndPlace).find()) {
                    place = Integer.parseInt(resultSubstring.replaceAll("\\D+", ""));
                } else {
                    String[] results = resultSubstring.split("-");
                    try {
                        matchesWon = Integer.parseInt(results[0]);
                        matchesLost = Integer.parseInt(results[1]);
                        matchesDrawn = Integer.parseInt(results[2]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                    }
                }
                player = playerAndPlace.substring(0, playerAndPlace.lastIndexOf("(")).trim();
            }
            DeckScraperModel deck = new DeckScraperModel();

            Element columnListElement = element.selectFirst("div.decklist-category-columns");
            List<CardScraperModel> cards = new ArrayList<>();
            if (columnListElement != null) {
                cards.addAll(scrapeCards(columnListElement));

            }

            Element sideboardElement = element.selectFirst("ul.decklist-sideboard");
            if (sideboardElement != null) {
                cards.addAll(scrapeCategory(sideboardElement, true));
            }
            deck.setCards(cards);

            deck.setDeckName("test deck name");
            deck.setPlayer(player);
            deck.setPlace(place);

            deck.setGamesWon(matchesWon);
            deck.setGamesLost(matchesLost);
            deck.setGamesDrawn(matchesDrawn);

            return deck;
        } catch (Exception e) {
            log.error("Error while scraping deck", e);
            return null;
        }
    }


    public List<CardScraperModel> scrapeCards(Element columnListElement){
        List<CardScraperModel> cards = new ArrayList<>();
        columnListElement.children().stream()
                .filter(category -> category.className().equals("decklist-category"))
                .forEach(category -> {
                    try {
                        List<CardScraperModel> cardsFromCategory = scrapeCategory(category, false);
                        cards.addAll(cardsFromCategory);
                    } catch (Exception ignored) {
                    }
        });
        return cards;
    }

    public List<CardScraperModel> scrapeCategory(Element categoryElement, Boolean sideboard){
        List<CardScraperModel> cards = new ArrayList<>();
        try {
            Element headerElement = categoryElement.selectFirst("h3.decklist-category-name");
            String categoryName;
            if (headerElement != null) {
                categoryName = headerElement.text().split(" ")[0];
            } else {
                categoryName = "";
            }
            List<Element> cardElements = categoryElement.select("a.decklist-card-link");
            cardElements.forEach(
                    card -> {
                        CardScraperModel cardModel = scrapeCard(card, sideboard);
                            if (cardModel != null) {
                            if (!sideboard){
                                cardModel.setCategoryName(categoryName);
                            } else {
                                cardModel.setCategoryName("Sideboard");
                            }
                            cards.add(cardModel);
                        }
                    }
            );
        } catch (Exception e) {
            log.error("Error while scraping category", e);
            return cards;
        }
        return cards;
    }

    public CardScraperModel scrapeCard(Element cardElement, Boolean sideboard){
        try {
            String[] quantityAndName = cardElement.text().split(" ", 2);
            Integer quantity = Integer.parseInt(quantityAndName[0]);
            String name = getFormattedCardName(quantityAndName[1]);

            CardScraperModel card = new CardScraperModel();
            card.setName(name);
            card.setQuantity(quantity);
            card.setSideboard(sideboard);
            return card;
        } catch (Exception e) {
            log.error("Error while scraping card", e);
            return null;
        }
    }

    private String getFormattedCardName(String name) {
        return name.replace("/", " // ");
    }
}