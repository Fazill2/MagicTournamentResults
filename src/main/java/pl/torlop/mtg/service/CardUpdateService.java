package pl.torlop.mtg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.torlop.mtg.model.api.BulkData;
import pl.torlop.mtg.model.api.BulkDataItem;

@Service
public class CardUpdateService {

    @Value("${scryfall.bulk.url}")
    private String cardUpdateUrl;

    @Autowired
    private CardService cardService;

    public void updateCards() {
        RestTemplate restTemplate = new RestTemplate();
        // parse json to bulkData
        BulkData bulkData = restTemplate.getForObject(cardUpdateUrl, BulkData.class);
        if (bulkData == null) {
            return;
        }
        BulkDataItem oracleCards = bulkData.getData().stream()
                .filter(bulkDataItem -> bulkDataItem.getType().equals("oracle_carsds"))
                .toList().get(0);

        String fileDownloadUrl = oracleCards.getDownload_uri();
        // download file


    }


}
