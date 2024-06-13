package pl.torlop.mtg.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.service.CardStatisticsService;
import pl.torlop.mtg.service.CardUpdateService;
import pl.torlop.mtg.service.ScraperDataSaverService;
import pl.torlop.mtg.service.TournamentScraperService;
import pl.torlop.mtg.service.repository.CardRepositoryService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledJobs {
    private final CardUpdateService cardUpdateService;
    private final List<TournamentScraperService> tournamentScraperServices;
    private final ScraperDataSaverService scraperDataSaverService;
    private final CardStatisticsService cardStatisticsService;
    private final CardRepositoryService cardRepositoryService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCardsDaily() {
        log.info("[Updating cards]");
        List<Card> cardEntities = cardUpdateService.getOracleCards();

        cardRepositoryService.saveAll(cardEntities);
    }


    @Scheduled(cron = "0 0 1/2 * * *")
    public void scrapeTournamentsDaily() {
        LocalDateTime beginDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        log.info("[Scraping tournaments from {} to {}]", beginDate, endDate);
        for (TournamentScraperService tournamentScraperService : tournamentScraperServices) {
            tournamentScraperService.scrapeTournaments(beginDate, endDate)
                    .forEach(scraperDataSaverService::saveTournamentData);
        }
    }

    @Scheduled(cron = "0 0 0/2 * * *")
    public void generateStatisticsDaily() {
        log.info("[Generating statistics]");
        cardStatisticsService.generateStatistics();
    }
}
