package pl.torlop.mtg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.torlop.mtg.model.scryfall_api.BulkData;
import pl.torlop.mtg.model.scryfall_api.BulkDataItem;
import pl.torlop.mtg.model.scryfall_api.CardItem;
import pl.torlop.mtg.model.entity.Card;
import pl.torlop.mtg.service.repository.CardRepositoryService;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardUpdateService {

    @Value("${scryfall.bulk.url}")
    private String cardUpdateUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<Card> getOracleCards(){
        String fileDownloadUrl = getBulkDataDownloadUri(cardUpdateUrl);
        if (fileDownloadUrl == null) {
            log.error("Error while updating cards: fileDownloadUrl is null");
            return null;
        }

        try {
            List<CardItem> cardItems = getCardItemsFromUrl(fileDownloadUrl);
            return getCardsFromCardList(cardItems);
        } catch (Exception e) {
            log.error("Error while updating cards", e);
            return null;
        }
    }


    public String getBulkDataDownloadUri(String url) {
        BulkData bulkData = restTemplate.getForObject(url, BulkData.class);
        if (bulkData == null) {
            return null;
        }
        BulkDataItem oracleCards = bulkData.getData().stream()
                .filter(bulkDataItem -> bulkDataItem.getType().equals("oracle_cards"))
                .toList().get(0);
        return oracleCards.getDownload_uri();
    }

    public List<CardItem> getCardItemsFromUrl(String fileDownloadUrl) throws IOException {
        URL url = new URL(fileDownloadUrl);

        return Arrays.asList(objectMapper.readValue(url, CardItem[].class));
    }

    private List<Card> getCardsFromCardList(List<CardItem> cardItems) {

        return cardItems.stream()
                .filter(cardItem -> Objects.equals(cardItem.getObject(), "card")
                        && !cardItem.layout.equals("token") && !cardItem.layout.equals("art_series")
                        && !cardItem.layout.equals("double_faced_token")
                        && isCardLegalAnywhere(cardItem) && !cardItem.set_type.equals("memorabilia"))
                .map(this::createCardEntityFromCardItem).toList();

    }

    public Boolean isCardLegalAnywhere(CardItem card) {
        String[] legalities = card.getLegalities().values();
        return  Arrays.stream(legalities).anyMatch(legality -> !legality.equals("not_legal"));
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
