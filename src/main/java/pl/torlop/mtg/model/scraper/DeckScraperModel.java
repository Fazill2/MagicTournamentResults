package pl.torlop.mtg.model.scraper;

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
