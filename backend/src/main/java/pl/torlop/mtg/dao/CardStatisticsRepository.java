package pl.torlop.mtg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.torlop.mtg.model.entity.CardStatistics;

import java.util.List;

public interface CardStatisticsRepository extends JpaRepository<CardStatistics, Long> {
    @Query("SELECT c FROM CardStatistics c WHERE c.format = :format AND c.timeScope = :timeScope AND c.isSideboard = :isSideboard ORDER BY c.count DESC LIMIT 50")
    List<CardStatistics> getTop50CardsByFormatAndTimeScopeAndIsSideboard(String format, String timeScope, boolean isSideboard);

    @Query("SELECT c FROM CardStatistics c WHERE c.format = :format AND c.timeScope = :timeScope AND c.isSideboard = :isSideboard ORDER BY c.count DESC LIMIT :limit")
    List<CardStatistics> getTopCardsByFormatAndTimeScopeAndIsSideboard(String format, String timeScope, boolean isSideboard, int limit);

    @Query("SELECT c FROM CardStatistics c WHERE c.card.id = :id AND  c.format = :format AND c.timeScope = :timeScope AND c.isSideboard = :isSideboard")
    CardStatistics getCardStatisticsByCardIdAndFormatAndTimeScopeAndIsSideboard(Long id, String format, String timeScope, boolean isSideboard);
}
