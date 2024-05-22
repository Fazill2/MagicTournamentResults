package pl.torlop.mtg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.torlop.mtg.scraper.model.TournamentScraperModel;
import pl.torlop.mtg.service.TournamentScraperService;

import java.util.List;

@RestController
@RequestMapping(path = "/tournament")
public class TournamentController {
    @Autowired
    TournamentScraperService scraperService;

    @GetMapping(path = "/scrapeTest")
    public List<TournamentScraperModel> scrapeTournaments() {
        return  scraperService.scrapeTournaments();
    }
}
