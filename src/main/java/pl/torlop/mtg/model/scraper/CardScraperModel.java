package pl.torlop.mtg.model.scraper;

import lombok.Data;

@Data
public class CardScraperModel {
    String name;
    String imageUrl;
    Integer quantity;
    String type;
}
