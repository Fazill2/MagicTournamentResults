package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Tournament;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByFormat(String format);
    List<Tournament> findByDate(LocalDateTime date);
}
