package pl.torlop.mtg.model.scryfall_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkDataItem {
    String object;
    String id;
    String type;
    String updated_at;
    String uri;
    String name;
    String description;
    String download_uri;
    String size;
    String content_type;
    String content_encoding;
}
