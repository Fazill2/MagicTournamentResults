package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.DeckCardRepository;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckCardRepositoryService {
    private DeckCardRepository deckCardRepository;

    List<DeckCard> getAllDeckCards(){
        return deckCardRepository.findAll();
    }
}
