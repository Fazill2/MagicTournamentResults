package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.TimeScope;
import pl.torlop.mtg.model.entity.CardStatistics;
import pl.torlop.mtg.service.repository.CardRepositoryService;
import pl.torlop.mtg.service.repository.CardStatisticsRepositoryService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardStatisticsService {
    private final CardRepositoryService cardRepositoryService;
    private final CardStatisticsRepositoryService cardStatisticsRepositoryService;

    public void generateStatistics(){
        cardStatisticsRepositoryService.deleteAllCardStatistics();
        for (String timeScope : TimeScope.TIME_SCOPES) {
            generateStatistics(timeScope);
        }
    }

    private void generateStatistics(String timeScope){
        LocalDateTime startDate = getStartDateForTimeScope(timeScope);
        LocalDateTime endDate = LocalDateTime.now();
        List<Object[]> statistics = cardRepositoryService.getListOfCardCountBetweenDates(startDate, endDate);
        List<CardStatistics> statisticsList = new ArrayList<>();
        for (Object[] stat : statistics) {
            CardStatistics cardStatistics = new CardStatistics();
            cardStatistics.setCard(cardRepositoryService.getCard((String) stat[0]));
            cardStatistics.setCount((Long) stat[1]);
            cardStatistics.setAverage_quantity(((BigDecimal) stat[2]).doubleValue());
            cardStatistics.setFormat((String) stat[3]);
            cardStatistics.setTimeScope(timeScope);
            cardStatistics.setIsSideboard((Boolean) stat[4]);
            statisticsList.add(cardStatistics);
        }
        cardStatisticsRepositoryService.saveAllCardStatistics(statisticsList);
    }

    public LocalDateTime getStartDateForTimeScope(String timeScope){
        return TimeScope.getStartDateForTimeScope(timeScope);
    }
}
