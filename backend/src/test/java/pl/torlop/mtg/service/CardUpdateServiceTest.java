package pl.torlop.mtg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import pl.torlop.mtg.model.scryfall_api.BulkData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CardUpdateServiceTest {
    @InjectMocks
    private CardUpdateService cardUpdateService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Value("${scryfall.bulk.url}")
    private String cardUpdateUrl;

    AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(cardUpdateService, "cardUpdateUrl", "https://example.com");
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    private BulkData createBulkData() throws JsonProcessingException {
        String exampleBulkDataString = "{\"object\":\"list\",\"has_more\":false,\"data\":[{\"object\":\"bulk_data\",\"id\":\"27bf3214-1271-490b-bdfe-c0be6c23d02e\",\"type\":\"oracle_cards\",\"updated_at\":\"2024-06-13T21:02:30.702+00:00\",\"uri\":\"https://api.scryfall.com/bulk-data/27bf3214-1271-490b-bdfe-c0be6c23d02e\",\"name\":\"Oracle Cards\",\"description\":\"A JSON file containing one Scryfall card object for each Oracle ID on Scryfall. The chosen sets for the cards are an attempt to return the most up-to-date recognizable version of the card.\",\"size\":147119262,\"download_uri\":\"https://data.scryfall.io/oracle-cards/oracle-cards-20240613210230.json\",\"content_type\":\"application/json\",\"content_encoding\":\"gzip\"},{\"object\":\"bulk_data\",\"id\":\"6bbcf976-6369-4401-88fc-3a9e4984c305\",\"type\":\"unique_artwork\",\"updated_at\":\"2024-06-13T21:04:22.769+00:00\",\"uri\":\"https://api.scryfall.com/bulk-data/6bbcf976-6369-4401-88fc-3a9e4984c305\",\"name\":\"Unique Artwork\",\"description\":\"A JSON file of Scryfall card objects that together contain all unique artworks. The chosen cards promote the best image scans.\",\"size\":205292921,\"download_uri\":\"https://data.scryfall.io/unique-artwork/unique-artwork-20240613210422.json\",\"content_type\":\"application/json\",\"content_encoding\":\"gzip\"},{\"object\":\"bulk_data\",\"id\":\"e2ef41e3-5778-4bc2-af3f-78eca4dd9c23\",\"type\":\"default_cards\",\"updated_at\":\"2024-06-13T21:09:17.666+00:00\",\"uri\":\"https://api.scryfall.com/bulk-data/e2ef41e3-5778-4bc2-af3f-78eca4dd9c23\",\"name\":\"Default Cards\",\"description\":\"A JSON file containing every card object on Scryfall in English or the printed language if the card is only available in one language.\",\"size\":446280206,\"download_uri\":\"https://data.scryfall.io/default-cards/default-cards-20240613210917.json\",\"content_type\":\"application/json\",\"content_encoding\":\"gzip\"},{\"object\":\"bulk_data\",\"id\":\"922288cb-4bef-45e1-bb30-0c2bd3d3534f\",\"type\":\"all_cards\",\"updated_at\":\"2024-06-13T21:21:54.665+00:00\",\"uri\":\"https://api.scryfall.com/bulk-data/922288cb-4bef-45e1-bb30-0c2bd3d3534f\",\"name\":\"All Cards\",\"description\":\"A JSON file containing every card object on Scryfall in every language.\",\"size\":2164218994,\"download_uri\":\"https://data.scryfall.io/all-cards/all-cards-20240613212154.json\",\"content_type\":\"application/json\",\"content_encoding\":\"gzip\"},{\"object\":\"bulk_data\",\"id\":\"06f54c0b-ab9c-452d-b35a-8297db5eb940\",\"type\":\"rulings\",\"updated_at\":\"2024-06-13T21:00:40.432+00:00\",\"uri\":\"https://api.scryfall.com/bulk-data/06f54c0b-ab9c-452d-b35a-8297db5eb940\",\"name\":\"Rulings\",\"description\":\"A JSON file containing all Rulings on Scryfall. Each ruling refers to cards via an `oracle_id`.\",\"size\":20228989,\"download_uri\":\"https://data.scryfall.io/rulings/rulings-20240613210040.json\",\"content_type\":\"application/json\",\"content_encoding\":\"gzip\"}]}";
        return new ObjectMapper().readValue(exampleBulkDataString, BulkData.class);
    }

    @Test
    void givenBulkData_whenGetBulkDataDownloadUri_thenReturnDownloadUri() throws JsonProcessingException {
        BulkData bulkData = createBulkData();
        when(restTemplate.getForObject("https://example.com", BulkData.class)).thenReturn(bulkData);
        ReflectionTestUtils.setField(cardUpdateService, "restTemplate", restTemplate);
        String downloadUri = cardUpdateService.getBulkDataDownloadUri("https://example.com");
        assertEquals("https://data.scryfall.io/oracle-cards/oracle-cards-20240613210230.json", downloadUri);
    }


}