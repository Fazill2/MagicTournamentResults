package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.CardStatistics;

public interface CardStatisticsRepository extends JpaRepository<CardStatistics, Long> {
}
