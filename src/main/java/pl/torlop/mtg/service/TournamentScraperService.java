package pl.torlop.mtg.service;

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
import pl.torlop.mtg.scraper.model.CardScraperModel;
import pl.torlop.mtg.scraper.model.DeckScraperModel;
import pl.torlop.mtg.scraper.model.TournamentScraperModel;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TournamentScraperService {
    //Reading data from property file to a list
    @Value("${scraper.url}")
    private String url;
    @Value("${scraper.baseURL}")
    private String baseURL;

    private final Pattern pattern = Pattern.compile("[(].*(st|nd|rd|th).*[)]");

    public List<TournamentScraperModel> scrapeTournaments() {
        try {
            Document document = Jsoup.connect(url).get();
            Element tournamentList = document.selectFirst("ul.decklists-list");
            // for each child element with class decklists-link and display different from none
            if (tournamentList == null) {
                throw new RuntimeException("No tournament list found");
            }
            List<TournamentScraperModel> tournaments = new ArrayList<>();
            tournamentList.children().stream()
                    .filter(element -> element.className().equals("decklists-item"))
                    .limit(3)
                    .forEach(element -> {
                        TournamentScraperModel tournament = scrapeTournament(element);
                        if (tournament != null) {
                            tournaments.add(tournament);
                        }
                    });
            return tournaments;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TournamentScraperModel scrapeTournament(Element element) {
        try {
            Element linkElement = element.selectFirst("a.decklists-link");
            if (linkElement == null) {
                return null;
            }
            String title = linkElement.selectFirst("div.decklists-details").selectFirst("h3").text();
            String date = linkElement.selectFirst("time.decklists-date").attr("datetime");
            String url = linkElement.attr("href");
            TournamentScraperModel tournament = new TournamentScraperModel();
            tournament.setTitle(title);
            tournament.setUrl(url);
            tournament.setDate(date);
            List<DeckScraperModel> decks = scrapeDecks(url);
            tournament.setDecks(decks);
            tournament.setPlayers(decks.size());
            return tournament;
        } catch (Exception e) {
            return null;
        }
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
            String playerAndPlace = element.selectFirst("header.decklist-header").selectFirst("p.decklist-player").text();
            // if pattern matches
            Integer place = null;
            Integer matchesWon = null;
            Integer matchesLost = null;
            Integer matchesDrawn = null;
            String resultSubstring = playerAndPlace.substring(playerAndPlace.lastIndexOf("(") + 1, playerAndPlace.lastIndexOf(")"));
            if (pattern.matcher(playerAndPlace).find()) {
                place = Integer.parseInt(resultSubstring.replaceAll("\\D+", ""));
            } else {
                String[] results = resultSubstring.split("-");
                try {
                    matchesWon = Integer.parseInt(results[0]);
                    matchesLost =  Integer.parseInt(results[1]);
                    matchesDrawn = Integer.parseInt(results[2]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                }
            }
            String player = playerAndPlace.substring(0, playerAndPlace.lastIndexOf("(")).trim();
            DeckScraperModel deck = new DeckScraperModel();
            Element columnListElement = element.selectFirst("div.decklist-category-columns");
            if (columnListElement != null) {
                List<CardScraperModel> cards = scrapeCards(columnListElement);
                deck.setCards(cards);
            }
            deck.setDeckName("test deck name");
            deck.setPlayer(player);
            deck.setPlace(place);
            deck.setGamesWon(matchesWon);
            deck.setGamesLost(matchesLost);
            deck.setGamesDrawn(matchesDrawn);
            return deck;
        } catch (Exception e) {
            return null;
        }
    }


    public List<CardScraperModel> scrapeCards(Element columnListElement){
        List<CardScraperModel> cards = new ArrayList<>();
        columnListElement.children().stream()
                .filter(category -> category.className().equals("decklist-category"))
                .forEach(category -> {
                    try {
                        List<CardScraperModel> cardsFromCategory = scrapeCategory(category);
                        cards.addAll(cardsFromCategory);
                    } catch (Exception e) {
                        return;
                    }
        });
        return cards;
    }

    public List<CardScraperModel> scrapeCategory(Element categoryElement){
        List<CardScraperModel> cards = new ArrayList<>();
        try {
            String type = categoryElement.selectFirst("h3")
                    .text().replaceAll("[^a-zA-z]+", "");
            List<Element> cardElements = categoryElement.select("a.decklist-card-link");
            cardElements.forEach(
                    card -> {
                        CardScraperModel cardModel = scrapeCard(card, type);
                        if (cardModel != null) {
                            cards.add(cardModel);
                        }
                    }
            );
        } catch (Exception e) {
            return cards;
        }
        return cards;
    }

    public CardScraperModel scrapeCard(Element cardElement, String type){
        try {
            String imageUrl = cardElement.attr("data-card-image");
            String[] quantityAndName = cardElement.text().split(" ", 2);
            Integer quantity = Integer.parseInt(quantityAndName[0]);
            String name = quantityAndName[1];
            CardScraperModel card = new CardScraperModel();
            card.setName(name);
            card.setImageUrl(imageUrl);
            card.setQuantity(quantity);
            card.setType(type);
            return card;
        } catch (Exception e) {
            return null;
        }
    }
}