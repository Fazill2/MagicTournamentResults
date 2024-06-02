package pl.torlop.mtg.model.dto;

public record DeckCardDto(
        Long id,
        String name,
        Integer quantity,
        Boolean sideboard,
        CardDto card,
        String category
) {
}
