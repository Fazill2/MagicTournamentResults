package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.service.DeckRepositoryService;
import pl.torlop.mtg.service.DeckUtilsService;

@RestController
@RequestMapping(path = "/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckUtilsService deckUtilsService;
    private final DeckRepositoryService deckRepositoryService;

    @GetMapping(path = "/color/{id}")
    public String getDeckColor(@PathVariable Long id) {
        Deck deck = deckRepositoryService.getDeck(id);
        return deckUtilsService.getDeckColor(deck).toString();
    }
}
