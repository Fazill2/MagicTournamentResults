package pl.torlop.mtg.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.model.entity.DeckCard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckUtilsServiceTest {
    DeckUtilsService deckUtilsService = new DeckUtilsService();
    Deck deck;
    List<DeckCard> deckCards;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setAverageCMC(2.0);

        DeckCard deckCard = new DeckCard();
        Card card = new Card();
        card.setColorIdentity(List.of("W"));
        card.setCmc(2);
        card.setTypeLine("Instant");
        deckCard.setCard(card);
        deckCard.setQuantity(2);

        DeckCard deckCard2 = new DeckCard();
        Card card2 = new Card();
        card2.setColorIdentity(List.of("U"));
        card2.setCmc(3);
        card2.setTypeLine("Creature");
        deckCard2.setCard(card2);
        deckCard2.setQuantity(2);

        deckCards = List.of(deckCard, deckCard2);
        deck.setCards(deckCards);
    }


    @Test
    void givenDeck_whenGetDeckColorString_returnColorAsString() {
        String expectedColor = "WU";
        String actualColor = deckUtilsService.getDeckColorString(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenNullDeck_whenGetDeckColorString_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckColorString(null));
    }

    @Test
    void givenDeckWithEmptyCards_whenGetDeckColorString_returnEmptyString() {
        deck.setCards(List.of());
        String expectedColor = "";
        String actualColor = deckUtilsService.getDeckColorString(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenDeckWithNullCard_whenGetDeckColorString_returnEmptyString() {
        deck.setCards(List.of(new DeckCard()));
        String expectedColor = "";
        String actualColor = deckUtilsService.getDeckColorString(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenDeckWithNullCards_whenGetDeckColorString_throwNullPointerException() {
        deck.setCards(null);

        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckColorString(deck));
    }

    @Test
    void givenDeck_whenGetDeckColor_returnColorList() {
        List<String> expectedColor = List.of("U", "W");
        List<String> actualColor = deckUtilsService.getDeckColor(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenNullDeck_whenGetDeckColor_throwNullPointerException() {
        deck = null;

        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckColor(deck));
    }

    @Test
    void givenDeckWithEmptyCards_whenGetDeckColor_returnEmptyList() {
        deck.setCards(List.of());
        List<String> expectedColor = List.of();
        List<String> actualColor = deckUtilsService.getDeckColor(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenDeckWithNullCard_whenGetDeckColor_returnEmptyList() {
        deck.setCards(List.of(new DeckCard()));
        List<String> expectedColor = List.of();
        List<String> actualColor = deckUtilsService.getDeckColor(deck);

        assertEquals(expectedColor, actualColor);
    }

    @Test
    void givenDeckWithNullCards_whenGetDeckColor_throwNullPointerException() {
        deck.setCards(null);

        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckColor(deck));
    }

    @Test
    void givenDeck_whenGetDeckAverageCMC_returnAverageCMC() {
        double expectedAverageCMC = 2.5;
        double actualAverageCMC = deckUtilsService.getDeckAverageCMC(deck);

        assertEquals(expectedAverageCMC, actualAverageCMC);
    }

    @Test
    void givenDeckWithCardWithoutTypes_whenGetDeckAverageCMC_throwNullPointerException() {
        deckCards.get(0).getCard().setTypeLine(null);

        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckAverageCMC(deck));
    }

    @Test
    void givenDeckWithLands_whenGetDeckAverageCMC_returnAverageCMCIgnoringLands() {
        deckCards.get(0).getCard().setTypeLine("Land");
        double expectedAverageCMC = 3.0;
        double actualAverageCMC = deckUtilsService.getDeckAverageCMC(deck);

        assertEquals(expectedAverageCMC, actualAverageCMC);
    }

    @Test
    void givenDeckWithNullCard_whenGetDeckAverageCMC_returnAverageCMCIgnoringNullCards() {
        deckCards.get(0).setCard(null);
        double expectedAverageCMC = 3.0;
        double actualAverageCMC = deckUtilsService.getDeckAverageCMC(deck);

        assertEquals(expectedAverageCMC, actualAverageCMC);
    }

    @Test
    void givenDeckWithNullCards_whenGetDeckAverageCMC_throwNullPointerException() {
        deck.setCards(null);

        assertThrows(NullPointerException.class, () -> deckUtilsService.getDeckAverageCMC(deck));
    }
}