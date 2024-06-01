package pl.torlop.mtg.model.scraper;

import lombok.Data;

@Data
public class CardScraperModel {
    String name;
    Integer quantity;
    Boolean sideboard;
    String categoryName;
}
