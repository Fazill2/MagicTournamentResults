package pl.torlop.mtg.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cards")
public class Card {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String manaCost;
    private Integer cmc;
    private String oracleText;
    private String typeLine;
    private String set;
    private String artist;
    private String rarity;
    @ElementCollection(targetClass = String.class)
    private List<String> colorIdentity;
    @ElementCollection(targetClass = String.class)
    private List<String> colors = new ArrayList<>();;
}
