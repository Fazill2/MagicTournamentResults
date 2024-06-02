package pl.torlop.mtg.model.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import pl.torlop.mtg.model.entity.Deck;

import java.time.LocalDateTime;
import java.util.List;

public record TournamentDto(
        Long id,
        String name,
        LocalDateTime date,
        Integer players,
        String format,
        String url,
        List<DeckDto> decks){
}
