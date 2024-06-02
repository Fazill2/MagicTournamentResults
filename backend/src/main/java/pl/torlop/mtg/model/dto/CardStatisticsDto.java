package pl.torlop.mtg.model.dto;

public record CardStatisticsDto(
        Long id,
        Long count,
        Double average_quantity,
        String format,
        String timeScope,
        Boolean isSideboard,
        CardDto card
) {
}