package pl.torlop.mtg.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.torlop.mtg.service.CardStatisticsService;
import pl.torlop.mtg.service.CardUpdateService;
import pl.torlop.mtg.service.ScraperDataSaverService;
import pl.torlop.mtg.service.TournamentScraperService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledJobs {
    private final CardUpdateService cardUpdateService;
    private final List<TournamentScraperService> tournamentScraperServices;
    private final ScraperDataSaverService scraperDataSaverService;
    private final CardStatisticsService cardStatisticsService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCardsDaily() {
        cardUpdateService.updateCards();
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void scrapeTournamentsDaily() {
        LocalDateTime beginDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        for (TournamentScraperService tournamentScraperService : tournamentScraperServices) {
            tournamentScraperService.scrapeTournaments(beginDate, endDate)
                    .forEach(scraperDataSaverService::saveTournamentData);
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void generateStatisticsDaily() {
        cardStatisticsService.generateStatistics();
    }
}
