package pl.torlop.mtg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.torlop.mtg.model.TimeScope;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.model.entity.CardStatistics;
import pl.torlop.mtg.model.entity.Deck;
import pl.torlop.mtg.service.repository.CardRepositoryService;
import pl.torlop.mtg.service.CardStatisticsService;
import pl.torlop.mtg.service.CardUpdateService;
import pl.torlop.mtg.service.repository.CardStatisticsRepositoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/card")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CardController {
    private final CardRepositoryService cardRepositoryService;
    private final CardUpdateService cardUpdateService;
    private final CardStatisticsService cardStatisticsService;
    private final CardStatisticsRepositoryService cardStatisticsRepositoryService;

    @GetMapping(path = "/details")
    public Card getCard(@RequestParam String id) {
        return cardRepositoryService.getCard(id);
    }

    @GetMapping(path = "/getTopCards")
    public List<CardStatistics> getTopCards(@RequestParam String format, @RequestParam String timeScope, @RequestParam boolean isSideboard){
        return cardStatisticsRepositoryService.getTop50CardsByFormatAndTimeScopeAndIsSideboard(format, timeScope, isSideboard);
    }

    @GetMapping(path = "/decks")
    public List<Deck> getDecksByCardId(@RequestParam String id){
        return cardRepositoryService.getDecksByCardId(id);
    }
}
