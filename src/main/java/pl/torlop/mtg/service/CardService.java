package pl.torlop.mtg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.CardRepository;
import pl.torlop.mtg.model.entity.Card;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

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

}
