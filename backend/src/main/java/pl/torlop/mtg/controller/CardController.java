package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.TimeScope;
import pl.torlop.mtg.model.aggregation.CardCount;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.service.CardRepositoryService;
import pl.torlop.mtg.service.CardStatisticsService;
import pl.torlop.mtg.service.CardUpdateService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/card")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CardController {
    private final CardRepositoryService cardRepositoryService;
    private final CardUpdateService cardUpdateService;
    private final CardStatisticsService cardStatisticsService;

    @GetMapping(path = "/details")
    public Card getCard(@RequestParam String id) {
        return cardRepositoryService.getCard(id);
    }

    @GetMapping(path = "/statistics")
    public List<Object[]> getCardStatistics(@RequestParam String id){
        cardStatisticsService.generateStatistics(TimeScope.ALL);
        return cardRepositoryService.getListOfCardCount(false);
    }

    @GetMapping(path = "/decks")
    public List<Deck> getDecksByCardId(@RequestParam String id){
        return cardRepositoryService.getDecksByCardId(id);
    }
}
