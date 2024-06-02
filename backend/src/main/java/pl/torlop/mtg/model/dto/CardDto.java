package pl.torlop.mtg.model.dto;

import java.util.List;

public record CardDto(
        String id,
        String name,
        String imageUrl,
        String backImageUrl,
        String manaCost,
        Integer cmc,
        String oracleText,
        String typeLine,
        String set,
        String artist,
        String rarity,
        List<String> colorIdentity,
        List<String> colors
) {
}
