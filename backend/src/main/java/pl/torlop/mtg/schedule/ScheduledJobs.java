package pl.torlop.mtg.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.torlop.mtg.service.CardUpdateService;
import pl.torlop.mtg.service.TournamentScraperService;

@Component
@RequiredArgsConstructor
public class ScheduledJobs {
    private final CardUpdateService cardUpdateService;
    private final TournamentScraperService tournamentScraperService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCardsDaily() {
        cardUpdateService.updateCards();
    }

}
