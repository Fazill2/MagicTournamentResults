package pl.torlop.mtg.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Tournament;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByFormat(String format);
    List<Tournament> findByDate(LocalDateTime date);
    Tournament getByNameAndDate(String name, LocalDateTime date);
    Tournament getByUrl(String url);
    List<Tournament> findTop10ByOrderByDateDesc();
    Page<Tournament> findAllByOrderByDateDesc(Pageable pageable);
    Page<Tournament> findAllByFormatOrderByDateDesc(String format, Pageable pageable);
}
