package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.entity.Tournament;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiUtilsService {
    public List<Tournament> getTournamentApiModels(List<Tournament> tournaments, boolean decks, boolean cards) {
        return tournaments.stream()
                .map(tournament -> getTournamentApiModel(tournament, decks, cards))
                .toList();
    }


    public Tournament getTournamentApiModel(Tournament tournament, boolean decks, boolean cards) {
        Tournament tournamentApiModel = new Tournament();
        tournamentApiModel.setId(tournament.getId());
        tournamentApiModel.setName(tournament.getName());
        tournamentApiModel.setDate(tournament.getDate());
        tournamentApiModel.setPlayers(tournament.getPlayers());
        tournamentApiModel.setFormat(tournament.getFormat());
        tournamentApiModel.setUrl(tournament.getUrl());
        if (decks) {
            if (cards){
                tournamentApiModel.setDecks(tournament.getDecks());
            } else {
                tournamentApiModel.setDecks(tournament.returnDecksWithoutCards());
            }
        }
        return tournamentApiModel;
    }
}
