package pl.torlop.mtg.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkData {
    String object;
    String has_more;
    List<BulkDataItem> data;
}
