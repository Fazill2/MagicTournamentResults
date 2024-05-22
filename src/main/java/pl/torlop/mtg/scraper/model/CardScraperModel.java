package pl.torlop.mtg.scraper.model;

import lombok.Data;

@Data
public class CardScraperModel {
    String name;
    String imageUrl;
    Integer quantity;
    String type;
}
