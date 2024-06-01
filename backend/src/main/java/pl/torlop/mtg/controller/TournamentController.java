package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;
import pl.torlop.mtg.model.scraper.TournamentScraperModel;
import pl.torlop.mtg.service.*;
import pl.torlop.mtg.service.repository.CardRepositoryService;
import pl.torlop.mtg.service.repository.TournamentRepositoryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/tournament")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TournamentController {
    private final MTGOTournamentScraperService scraperService;
    private final CardUpdateService cardUpdateService;
    private final ScraperDataSaverService scraperDataSaverService;
    private final TournamentRepositoryService tournamentRepositoryService;
    private final ApiUtilsService apiUtilsService;

    @GetMapping(path = "/scrapeTest")
    public List<TournamentScraperModel> scrapeTournaments() {
        LocalDateTime beginDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);
        List<TournamentScraperModel> tournaments = scraperService.scrapeTournaments(beginDate, endDate);
        tournaments.forEach(scraperDataSaverService::saveTournamentData);
        return tournaments;
    }

    @GetMapping(path = "/clearDatabase")
    public void clearDatabase() {
        tournamentRepositoryService.clearDatabase();
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
    public List<Tournament> getTop10Tournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTop10Tournaments(), false, false);
    }

    @GetMapping(path = "/all")
    public List<Tournament> getAllTournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTournaments(), false, false);
    }

    @GetMapping(path = "/page")
    public Page<Tournament> getTournamentsPage(@RequestParam(required = false) String format,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        return tournamentRepositoryService.getTournamentsByFormat(format, page, size);
    }

    @GetMapping(path = "/listDecks")
    @ResponseBody
    public List<Deck> getDecks(@RequestParam Long id) {
        return tournamentRepositoryService.getDecksFromTournament(id);
    }

    @GetMapping(path = "/details")
    @ResponseBody
    public Tournament getTournamentDetails(@RequestParam Long id) {
        return apiUtilsService.getTournamentApiModel(tournamentRepositoryService.getTournament(id), true, false);
    }
}
