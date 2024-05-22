package pl.torlop.mtg.model.scraper;


import lombok.Data;

import java.util.List;

@Data
public class TournamentScraperModel {
    String title;
    String url;
    String date;
    Integer players;
    String format;
    List<DeckScraperModel> decks;
}