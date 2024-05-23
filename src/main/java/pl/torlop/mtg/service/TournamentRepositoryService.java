package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.TournamentRepository;
import pl.torlop.mtg.model.entity.Tournament;

@Service
@RequiredArgsConstructor
public class TournamentRepositoryService {
    private final TournamentRepository tournamentRepository;

    public void saveTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}
