package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    Deck getAllByPlayer(String player);
}
