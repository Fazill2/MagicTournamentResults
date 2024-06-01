package pl.torlop.mtg.model.api;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckApiModel {
    private Long id;
    private String name;
    private String player;
    private Integer place;
    private Integer gamesLost;
    private Integer gamesWon;
    private Integer gamesDrawn;
    private List<DeckCard> cards;
    private Double averageCMC;
    private String color;
}
