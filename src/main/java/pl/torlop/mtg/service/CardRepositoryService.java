package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.CardRepository;
import pl.torlop.mtg.model.entity.Card;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardRepositoryService {
    private final CardRepository cardRepository;

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public Card getCard(String id) {
        return cardRepository.findById(id).orElse(null);
    }

    public void deleteCard(String name) {
        cardRepository.deleteById(name);
    }

    public void updateCard(Card card) {
        cardRepository.save(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAll();
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> getCardsByName(String name) {
        return cardRepository.findByName(name);
    }

    public List<Card> getCardsBySet(String set) {
        return cardRepository.findBySet(set);
    }

    public void saveAll(List<Card> cards) {
        cardRepository.saveAll(cards);
    }

    public Card getCardByName(String name) {
        List<Card> cards = cardRepository.findByName(name);
        if (cards.size() == 1) {
            return cards.get(0);
        } else {
            List<Card> cardsContaining = cardRepository.findByNameContaining(name);
            if (cardsContaining.size() == 1) {
                return cardsContaining.get(0);
            } else {
                return null;
            }
        }
    }
}
