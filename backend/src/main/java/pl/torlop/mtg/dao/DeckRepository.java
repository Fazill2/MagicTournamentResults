package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> getAllByPlayer(String player);
}
