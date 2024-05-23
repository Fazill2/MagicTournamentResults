package pl.torlop.mtg.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardFace{
    public String object;
    public String name;
    public String mana_cost;
    public String type_line;
    public String oracle_text;
    public ArrayList<String> colors;
    public String artist;
    public String artist_id;
    public String illustration_id;
    public ImageUris image_uris;
    public String flavor_name;
}
