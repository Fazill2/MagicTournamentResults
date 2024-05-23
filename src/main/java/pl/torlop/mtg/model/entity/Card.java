package pl.torlop.mtg.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.torlop.mtg.converter.StringListConverter;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "cards")
public class Card {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String backImageUrl;
    private String manaCost;
    private Integer cmc;
    private String oracleText;
    private String typeLine;
    private String set;
    private String artist;
    private String rarity;
    @Convert(converter = StringListConverter.class)
    private List<String> colorIdentity;
    @Convert(converter = StringListConverter.class)
    private List<String> colors;
}
