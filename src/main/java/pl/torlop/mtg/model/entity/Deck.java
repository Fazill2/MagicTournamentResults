package pl.torlop.mtg.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String player;
    private Integer place;
    private Integer gamesLost;
    private Integer gamesWon;
    private Integer gamesDrawn;
    @OneToMany(cascade = CascadeType.ALL)
    private List<DeckCard> cards;
}
