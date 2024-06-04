package pl.torlop.mtg.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.DeckRepository;
import pl.torlop.mtg.model.entity.Deck;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckRepositoryService {
    private final DeckRepository deckRepository;

    public void saveDeck(Deck deck) {
        deckRepository.save(deck);
    }

    public Deck getDeck(Long id) {
        return deckRepository.findById(id).orElse(null);
    }

    public void deleteDeck(Long id) {
        deckRepository.deleteById(id);
    }

    public List<Deck> getDecks() {
        return deckRepository.findAll();
    }

    public void clearDatabase() {
        deckRepository.deleteAll();
    }

    public List<Deck> getDecksWithCard(String cardId){
        return deckRepository.getDecksWithCard(cardId);
    }
}
