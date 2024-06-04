package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;
import pl.torlop.mtg.model.entity.Tournament;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> getAllByPlayer(String player);

    @Query("Select d FROM  Tournament t JOIN t.decks d JOIN d.cards dc JOIN Card c ON dc.card = c  WHERE c.id = :cardId ORDER by t.date DESC Limit 10")
    List<Deck> getDecksWithCard(String cardId);
}
