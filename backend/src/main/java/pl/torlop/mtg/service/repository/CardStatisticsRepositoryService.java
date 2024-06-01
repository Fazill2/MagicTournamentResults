package pl.torlop.mtg.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.CardStatisticsRepository;
import pl.torlop.mtg.model.entity.CardStatistics;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardStatisticsRepositoryService {
    private final CardStatisticsRepository cardStatisticsRepository;

    public void saveAllCardStatistics(List<CardStatistics> cardStatistics){
        cardStatisticsRepository.saveAll(cardStatistics);
    }

    public List<CardStatistics> getAllCardStatistics(){
        return cardStatisticsRepository.findAll();
    }

    public void deleteAllCardStatistics(){
        cardStatisticsRepository.deleteAll();
    }

    public List<CardStatistics> getTop50CardsByFormatAndTimeScopeAndIsSideboard(String format, String timeScope, boolean isSideboard){
        return cardStatisticsRepository.getTop50CardsByFormatAndTimeScopeAndIsSideboard(format, timeScope, isSideboard);
    }


}
