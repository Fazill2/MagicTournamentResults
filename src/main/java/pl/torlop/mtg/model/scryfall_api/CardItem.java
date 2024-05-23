package pl.torlop.mtg.model.scryfall_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardItem{
    public String object;
    public String id;
    public String oracle_id;
    public ArrayList<Integer> multiverse_ids;
    public int mtgo_id;
    public int mtgo_foil_id;
    public int tcgplayer_id;
    public int cardmarket_id;
    public String name;
    public String lang;
    public String released_at;
    public String uri;
    public String scryfall_uri;
    public String layout;
    public boolean highres_image;
    public String image_status;
    public ImageUris image_uris;
    public String mana_cost;
    public double cmc;
    public String type_line;
    public String oracle_text;
    public ArrayList<String> colors;
    public ArrayList<String> color_identity;
    public ArrayList<String> keywords;
    public Legalities legalities;
    public ArrayList<String> games;
    public boolean reserved;
    public boolean foil;
    public boolean nonfoil;
    public ArrayList<String> finishes;
    public boolean oversized;
    public boolean promo;
    public boolean reprint;
    public boolean variation;
    public String set_id;
    public String set;
    public String set_name;
    public String set_type;
    public String set_uri;
    public String set_search_uri;
    public String scryfall_set_uri;
    public String rulings_uri;
    public String prints_search_uri;
    public String collector_number;
    public boolean digital;
    public String rarity;
    public String flavor_text;
    public String card_back_id;
    public String artist;
    public ArrayList<String> artist_ids;
    public String illustration_id;
    public String border_color;
    public String frame;
    public boolean full_art;
    public boolean textless;
    public boolean booster;
    public boolean story_spotlight;
    public int edhrec_rank;
    public Prices prices;
    public RelatedUris related_uris;
    public PurchaseUris purchase_uris;
    public ArrayList<CardFace> card_faces;
}


