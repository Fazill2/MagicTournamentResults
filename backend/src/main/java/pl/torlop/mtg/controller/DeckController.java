package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.dto.DeckDto;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.service.EntityToApiModelService;
import pl.torlop.mtg.service.repository.DeckRepositoryService;
import pl.torlop.mtg.service.DeckUtilsService;

import java.util.List;

@RestController
@RequestMapping(path = "/deck")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DeckController {
    private final DeckUtilsService deckUtilsService;
    private final DeckRepositoryService deckRepositoryService;
    private final EntityToApiModelService entityToApiModelService;

    @GetMapping(path = "/color/{id}")
    public String getDeckColor(@PathVariable Long id) {
        Deck deck = deckRepositoryService.getDeck(id);
        return deckUtilsService.getDeckColorString(deck);
    }

    @GetMapping(path = "/details")
    public Deck getDeckDetails(@RequestParam Long id) {
        return deckRepositoryService.getDeck(id);
    }

    @GetMapping(path="/getDecksForCard")
    public List<DeckDto> getDecksForCard(@RequestParam String cardId){
        return entityToApiModelService.getDecksApiModel(deckRepositoryService.getDecksWithCard(cardId), false);
    }
}
