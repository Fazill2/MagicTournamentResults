package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeckUtilsService {

    public List<String> getDeckColor(Deck deck) {
        Set<String> colors = new HashSet<>();
        for (DeckCard deckCard : deck.getCards()) {
            colors.addAll(deckCard.getCard().getColorIdentity());
        }
        return colors.stream().toList();
    }
}
