package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.api.TournamentApiModel;
import pl.torlop.mtg.model.entity.Tournament;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiUtilsService {
    public List<TournamentApiModel> getTournamentApiModels(List<Tournament> tournaments) {
        return tournaments.stream()
                .map(this::getTournamentApiModel)
                .toList();
    }


    public TournamentApiModel getTournamentApiModel(Tournament tournament) {
        TournamentApiModel tournamentApiModel = new TournamentApiModel();
        tournamentApiModel.setId(tournament.getId());
        tournamentApiModel.setName(tournament.getName());
        tournamentApiModel.setDate(tournament.getDate());
        tournamentApiModel.setPlayers(tournament.getPlayers());
        tournamentApiModel.setFormat(tournament.getFormat());
        tournamentApiModel.setUrl(tournament.getUrl());
        return tournamentApiModel;
    }
}
