package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Deck;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByName(String name);
    List<Card> findByNameContaining(String name);
    List<Card> findByNameContainingIgnoreCaseOrderByName(String name);

    @Query(value = "SELECT cards.id, COUNT(deck_cards.quantity), AVG(deck_cards.quantity), tournaments.format, deck_cards.sideboard\n" +
            "FROM cards\n" +
            "JOIN deck_cards ON cards.id = deck_cards.card_id\n" +
            "JOIN decks_cards ON decks_cards.cards_id = deck_cards.id\n" +
            "JOIN decks ON decks.id = decks_cards.deck_id\n" +
            "JOIN tournaments_decks ON tournaments_decks.decks_id = decks.id\n" +
            "JOIN tournaments ON tournaments.id = tournaments_decks.tournament_id\n" +
            "GROUP BY cards.id, tournaments.format, deck_cards.sideboard", nativeQuery = true)
    List<Object[]> getListOfCardCount();

    @Query(value = "SELECT cards.id, COUNT(deck_cards.quantity), AVG(deck_cards.quantity), tournaments.format, deck_cards.sideboard\n" +
            "FROM cards\n" +
            "JOIN deck_cards ON cards.id = deck_cards.card_id\n" +
            "JOIN decks_cards ON decks_cards.cards_id = deck_cards.id\n" +
            "JOIN decks ON decks.id = decks_cards.deck_id\n" +
            "JOIN tournaments_decks ON tournaments_decks.decks_id = decks.id\n" +
            "JOIN tournaments ON tournaments.id = tournaments_decks.tournament_id\n" +
            "WHERE tournaments.date BETWEEN :startDate AND :endDate\n" +
            "GROUP BY cards.id, tournaments.format, deck_cards.sideboard", nativeQuery = true)
    List<Object[]> getListOfCardCountBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT d FROM Card c JOIN DeckCard dc ON dc.card.id = c.id JOIN Deck d WHERE c.id = :#{#id} AND dc.sideboard = FALSE")
    List<Deck> getDecksByCardId(String id);
}
