package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.torlop.mtg.model.scraper.TournamentScraperModel;
import pl.torlop.mtg.service.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tournament")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentScraperService scraperService;
    private final CardUpdateService cardUpdateService;
    private final ScraperDatabaseService scraperDatabaseService;
    private final TournamentRepositoryService tournamentRepositoryService;
    private final CardRepositoryService cardRepositoryService;

    @GetMapping(path = "/scrapeTest")
    public List<TournamentScraperModel> scrapeTournaments() {
        List<TournamentScraperModel> tournaments = scraperService.scrapeTournaments();
        tournaments.forEach(scraperDatabaseService::saveTournamentData);
        return tournaments;
    }

    @GetMapping(path = "/clearDatabase")
    public void clearDatabase() {
        tournamentRepositoryService.clearDatabase();
        cardRepositoryService.deleteAllCards();
    }

    @GetMapping(path = "/list")
    public String getCards() {
        return tournamentRepositoryService.getTournaments().toString();
    }

    @GetMapping(path = "/updateCards")
    public void updateCards() {
        cardUpdateService.updateCards();
    }
}
