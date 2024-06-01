package pl.torlop.mtg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeckUtilsService {

    public String getDeckColorString(Deck deck) {
        return getStringColorIdentity(getDeckColor(deck));
    }

    public List<String> getDeckColor(Deck deck) {
        Set<String> colors = new HashSet<>();
        for (DeckCard deckCard : deck.getCards()) {
            Card card = deckCard.getCard();
            if (card != null && card.getColorIdentity() != null
                    && !card.getColorIdentity().isEmpty()) {
                colors.addAll(deckCard.getCard().getColorIdentity());
            }
        }
        return colors.stream().toList();
    }

    private String getStringColorIdentity(List<String> colorIdentity) {
        String base = "WUBRG";
        StringBuilder sb = new StringBuilder();
        for (String color : base.split("")) {
            if (colorIdentity.contains(color)) {
                sb.append(color);
            }
        }
        return sb.toString();
    }

    public double getDeckAverageCMC(Deck deck) {
        double sum = 0;
        int count = 0;
        for (DeckCard deckCard : deck.getCards()) {
            if (deckCard.getCard() == null || deckCard.getCard().getTypeLine().contains("Land")) {
                continue;
            }
            sum += deckCard.getCard().getCmc() * deckCard.getQuantity();
            count += deckCard.getQuantity();
        }
        return sum / count;
    }
}
