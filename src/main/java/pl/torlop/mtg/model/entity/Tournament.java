package pl.torlop.mtg.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    private Integer players;
    private String format;
    private String url;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Deck> decks;
}
