package pl.torlop.mtg.model.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TournamentApiModel {
    private Long id;
    private String name;
    private LocalDateTime date;
    private Integer players;
    private String format;
    private String url;
}
