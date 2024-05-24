package pl.torlop.mtg.model.scraper;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TournamentScraperModel {
    String name;
    String url;
    LocalDateTime date;
    Integer players;
    String format;
    List<DeckScraperModel> decks;
}