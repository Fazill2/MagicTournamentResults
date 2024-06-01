package pl.torlop.mtg.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.List;

public interface DeckCardRepository extends JpaRepository<DeckCard, Long> {
}