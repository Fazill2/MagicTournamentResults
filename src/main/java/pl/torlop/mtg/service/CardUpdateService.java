package pl.torlop.mtg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.torlop.mtg.model.scryfall_api.BulkData;
import pl.torlop.mtg.model.scryfall_api.BulkDataItem;
import pl.torlop.mtg.model.scryfall_api.CardItem;
import pl.torlop.mtg.model.entity.Card;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CardUpdateService {

    @Value("${scryfall.bulk.url}")
    private String cardUpdateUrl;

    @Autowired
    private CardRepositoryService cardRepositoryService;

    public void updateCards() {
        RestTemplate restTemplate = new RestTemplate();
        BulkData bulkData = restTemplate.getForObject(cardUpdateUrl, BulkData.class);
        if (bulkData == null) {
            return;
        }

        BulkDataItem oracleCards = bulkData.getData().stream()
                .filter(bulkDataItem -> bulkDataItem.getType().equals("oracle_cards"))
                .toList().get(0);

        try {
            // wait between requests to avoid 429 error
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        String fileDownloadUrl = oracleCards.getDownload_uri();

        try {
            URL url = new URL(fileDownloadUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardItem> cardItems = Arrays.asList(objectMapper.readValue(url, CardItem[].class));

            List<Card> cardEntities = cardItems.stream()
                    .filter(cardItem -> Objects.equals(cardItem.getObject(), "card")
                            && !cardItem.layout.equals("token") && !cardItem.layout.equals("art_series")
                            && !cardItem.layout.equals("double_faced_token"))
                    .map(this::createCardEntityFromCardItem).toList();

            cardRepositoryService.saveAll(cardEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Card createCardEntityFromCardItem(CardItem cardItem) {
        Card card = new Card();
        card.setId(cardItem.getOracle_id());
        card.setName(cardItem.getName());

        if (cardItem.getImage_uris() == null){
            card.setImageUrl(cardItem.getCard_faces().get(0).getImage_uris().getNormal());
            card.setBackImageUrl(cardItem.getCard_faces().get(1).getImage_uris().getNormal());
            card.setManaCost(cardItem.getCard_faces().get(0).getMana_cost());
            card.setColors(cardItem.getCard_faces().get(0).getColors());
        } else {
            card.setImageUrl(cardItem.getImage_uris().getNormal());
            card.setManaCost(cardItem.getMana_cost());
            card.setColors(cardItem.getColors());
        }

        card.setCmc((int) cardItem.getCmc());
        card.setOracleText(cardItem.getOracle_text());
        card.setTypeLine(cardItem.getType_line());
        card.setSet(cardItem.getSet());
        card.setArtist(cardItem.getArtist());
        card.setRarity(cardItem.getRarity());
        card.setColorIdentity(cardItem.getColor_identity());

        return card;
    }


}
