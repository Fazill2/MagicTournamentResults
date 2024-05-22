package pl.torlop.mtg.scraper.model;


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