package pl.torlop.mtg.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.dao.CardRepository;
import pl.torlop.mtg.dao.CardStatisticsRepository;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.CardStatistics;
import pl.torlop.mtg.model.entity.Deck;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardRepositoryService {
    private final CardRepository cardRepository;

    public Card getCard(String id) {
        return cardRepository.findById(id).orElse(null);
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

    public List<Object[]> getListOfCardCount(){
        return cardRepository.getListOfCardCount();
    }

    public List<Deck> getDecksByCardId(String id) {
        return cardRepository.getDecksByCardId(id);
    }

    public List<Object[]> getListOfCardCountBetweenDates(LocalDateTime startDate, LocalDateTime endDate){
        return cardRepository.getListOfCardCountBetweenDates(startDate, endDate);
    }
}
