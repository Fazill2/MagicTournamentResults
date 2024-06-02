package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.dto.TournamentDto;
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
    private final TournamentRepositoryService tournamentRepositoryService;
    private final ApiUtilsService apiUtilsService;

    @GetMapping(path = "/last10")
    public List<TournamentDto> getTop10Tournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTop10Tournaments(), false, false);
    }

    @GetMapping(path = "/all")
    public List<TournamentDto> getAllTournaments() {
        return apiUtilsService.getTournamentApiModels(tournamentRepositoryService.getTournaments(), false, false);
    }

    @GetMapping(path = "/page")
    public Page<TournamentDto> getTournamentsPage(@RequestParam(required = false) String format,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        return apiUtilsService.getTournamentDtoPage(tournamentRepositoryService.getTournamentsByFormat(format, page, size));
    }

    @GetMapping(path = "/listDecks")
    @ResponseBody
    public List<Deck> getDecks(@RequestParam Long id) {
        return tournamentRepositoryService.getDecksFromTournament(id);
    }

    @GetMapping(path = "/details")
    @ResponseBody
    public TournamentDto getTournamentDetails(@RequestParam Long id) {
        return apiUtilsService.getTournamentApiModel(tournamentRepositoryService.getTournament(id), true, false);
    }
}
