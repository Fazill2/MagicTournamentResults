package pl.torlop.mtg.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.TournamentRepository;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }

    public void clearDatabase() {
        tournamentRepository.deleteAll();
    }

    public List<Tournament> getTournamentsByFormat(String format) {
        return tournamentRepository.findByFormat(format);
    }

    public List<Tournament> getTournamentsByDate(LocalDateTime date) {
        return tournamentRepository.findByDate(date);
    }

    public Tournament getTournamentByNameAndDate(String name, LocalDateTime date) {
        return tournamentRepository.getByNameAndDate(name, date);
    }

    public Tournament getTournamentByUrl(String url) {
        return tournamentRepository.getByUrl(url);
    }

    public List<Tournament> getTop10Tournaments() {
        return tournamentRepository.findTop10ByOrderByDateDesc();
    }

    public List<Deck> getDecksFromTournament(Long id) {
        return getTournament(id).getDecks();
    }
}