package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.api.TournamentApiModel;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;
import pl.torlop.mtg.model.scraper.TournamentScraperModel;
import pl.torlop.mtg.service.*;

import java.time.LocalDateTime;
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
    private final ApiUtilsService apiUtilsService;

    @GetMapping(path = "/scrapeTest")
    public List<TournamentScraperModel> scrapeTournaments() {
        LocalDateTime beginDate = LocalDateTime.now().minusDays(5);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);
        List<TournamentScraperModel> tournaments = scraperService.scrapeTournaments(beginDate, endDate);
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

    @GetMapping(path = "/last10")
    public List<TournamentApiModel> getTop10Tournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTop10Tournaments());
    }

    @GetMapping(path = "/all")
    public List<TournamentApiModel> getAllTournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTournaments());
    }

    @GetMapping(path = "/listDecks")
    @ResponseBody
    public List<Deck> getTournaments(@RequestParam Long id) {
        return tournamentRepositoryService.getDecksFromTournament(id);
    }
}
