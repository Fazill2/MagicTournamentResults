package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.TimeScope;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.CardStatistics;
import pl.torlop.mtg.model.entity.DeckCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardStatisticsService {
    private final CardRepositoryService cardRepositoryService;
    // 1 - card id
    // 2 - quantity
    // 3 - avg quantity
    // 4 - format
    // 5 - is sideboard

    public void generateStatistics(String timeScope){
        List<Object[]> statistics = cardRepositoryService.getListOfCardCount(true);
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
        cardRepositoryService.saveAllCardStatistics(statisticsList);
    }
}
