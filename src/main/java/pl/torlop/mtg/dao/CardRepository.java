package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByName(String name);
}
