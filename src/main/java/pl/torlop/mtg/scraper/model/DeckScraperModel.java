package pl.torlop.mtg.scraper.model;

import lombok.Data;

import java.util.List;

@Data
public class DeckScraperModel {
    String deckName;
    String player;
    Integer place;
    Integer gamesLost;
    Integer gamesWon;
    Integer gamesDrawn;
    List<CardScraperModel> cards;
}
