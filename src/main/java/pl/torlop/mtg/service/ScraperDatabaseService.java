package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;
import pl.torlop.mtg.model.entity.Tournament;
import pl.torlop.mtg.model.scraper.DeckScraperModel;
import pl.torlop.mtg.model.scraper.TournamentScraperModel;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScraperDatabaseService {
    private final CardRepositoryService cardRepositoryService;
    private final TournamentRepositoryService tournamentRepositoryService;

    public void saveTournamentData(TournamentScraperModel tournamentScraperModel) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentScraperModel.getName());
        LocalDateTime date = tournamentScraperModel.getDate();
        tournament.setDate(date);
        tournament.setFormat(tournamentScraperModel.getFormat());
        tournament.setPlayers(tournamentScraperModel.getPlayers());
        tournament.setUrl(tournamentScraperModel.getUrl());
        tournament.setFormat(tournamentScraperModel.getFormat());
        tournament.setDecks(getDecksFromTournament(tournamentScraperModel));
        tournamentRepositoryService.saveTournament(tournament);

        log.info("Saved tournament: {}", tournament.getUrl());
    }

    public List<Deck> getDecksFromTournament(TournamentScraperModel tournamentScraperModel) {
        return tournamentScraperModel.getDecks().stream()
                .map(deckScraperModel -> {
                    Deck deck = new Deck();
                    deck.setPlayer(deckScraperModel.getPlayer());
                    deck.setName(deckScraperModel.getDeckName());
                    deck.setGamesDrawn(deckScraperModel.getGamesDrawn());
                    deck.setGamesLost(deckScraperModel.getGamesLost());
                    deck.setGamesWon(deckScraperModel.getGamesWon());
                    deck.setPlace(deckScraperModel.getPlace());
                    deck.setCards(getDeckCardsFromDeck(deckScraperModel));
                    return deck;
                })
                .toList();
    }

    public List<DeckCard> getDeckCardsFromDeck(DeckScraperModel deck) {
        return deck.getCards().stream()
                .map(card -> {
                    DeckCard deckCard = new DeckCard();
                    deckCard.setCard(cardRepositoryService.getCardByName(card.getName()));
                    deckCard.setQuantity(card.getQuantity());
                    deckCard.setName(card.getName());
                    deckCard.setSideboard(card.getSideboard());
                    return deckCard;
                })
                .toList();
    }
}
