package pl.torlop.mtg.model.dto;


import pl.torlop.mtg.model.entity.DeckCard;

import java.util.List;

public record DeckDto(
        Long id,
        String name,
        String player,
        Integer place,
        Integer gamesLost,
        Integer gamesWon,
        Integer gamesDrawn,
        List<DeckCard> cards,
        Double averageCMC,
        String color) {
}