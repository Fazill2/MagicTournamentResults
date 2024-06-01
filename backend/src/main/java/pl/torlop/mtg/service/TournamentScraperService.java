package pl.torlop.mtg.service;

import pl.torlop.mtg.model.scraper.TournamentScraperModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentScraperService {
    List<TournamentScraperModel> scrapeTournaments(LocalDateTime beginDate, LocalDateTime endDate);
}
