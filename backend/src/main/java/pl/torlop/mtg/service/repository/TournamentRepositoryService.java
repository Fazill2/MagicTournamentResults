package pl.torlop.mtg.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.TournamentRepository;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }

    public void clearDatabase() {
        tournamentRepository.deleteAll();
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

    public Page<Tournament> getTournamentsByFormat(String format, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tournament> tournamentPage;
        if (format == null || format.isEmpty() || format.isBlank() || format.equals("All")) {
            tournamentPage = tournamentRepository.findAllByOrderByDateDesc(pageable);
        } else {
            tournamentPage = tournamentRepository.findAllByFormatOrderByDateDesc(format, pageable);
        }
        List<Tournament> tournaments = tournamentPage.getContent().stream()
                .peek(tournament -> {
                    tournament.setDecks(null);
                })
                .toList();
        return new PageImpl<>(tournaments, pageable, tournamentPage.getTotalElements());
    }
}
