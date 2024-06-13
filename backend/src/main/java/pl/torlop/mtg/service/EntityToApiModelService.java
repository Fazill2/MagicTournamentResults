package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.dto.DeckDto;
import pl.torlop.mtg.model.dto.TournamentDto;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;
import pl.torlop.mtg.model.entity.Tournament;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityToApiModelService {
    public Page<TournamentDto> getTournamentDtoPage(Page<Tournament> tournamentPage) {
        List<TournamentDto> tournamentDtos = getTournamentApiModels(tournamentPage.getContent(), false, false);
        return new PageImpl<>(tournamentDtos, tournamentPage.getPageable(), tournamentPage.getTotalElements());
    }

    public List<TournamentDto> getTournamentApiModels(List<Tournament> tournaments, boolean decks, boolean cards) {
        return tournaments.stream()
                .map(tournament -> getTournamentApiModel(tournament, decks, cards))
                .toList();
    }

    public TournamentDto getTournamentApiModel(Tournament tournament, boolean decks, boolean cards){
        List<DeckDto> deckDtos = null;
        if (decks) {
            if (cards){
                deckDtos = tournament.getDecks().stream().map(deck -> new DeckDto(deck.getId(), deck.getName(), deck.getPlayer(), deck.getPlace(), deck.getGamesLost(), deck.getGamesWon(), deck.getGamesDrawn(), deck.getCards(), deck.getAverageCMC(), deck.getColor())).toList();
            } else {
                deckDtos = tournament.getDecks().stream().map(deck -> new DeckDto(deck.getId(), deck.getName(), deck.getPlayer(), deck.getPlace(), deck.getGamesLost(), deck.getGamesWon(), deck.getGamesDrawn(), null, deck.getAverageCMC(), deck.getColor())).toList();
            }
        }
        return new TournamentDto(
                tournament.getId(),
                tournament.getName(),
                tournament.getDate(),
                tournament.getPlayers(),
                tournament.getFormat(),
                tournament.getUrl(),
                deckDtos
        );
    }

    public List<DeckDto> getDecksApiModel(List<Deck> decks, boolean cards){
        return decks.stream().map(deck -> getDeckApiModel(deck, cards)).toList();
    }

    public DeckDto getDeckApiModel(Deck deck, boolean cards){
        List<DeckCard> deckCards = (cards) ? deck.getCards() : null;
        return new DeckDto(deck.getId(), deck.getName(), deck.getPlayer(), deck.getPlace(), deck.getGamesLost(), deck.getGamesWon(), deck.getGamesDrawn(), deckCards, deck.getAverageCMC(), deck.getColor());
    }
}
