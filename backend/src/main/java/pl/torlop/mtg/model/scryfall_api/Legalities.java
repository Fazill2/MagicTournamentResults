package pl.torlop.mtg.model.scryfall_api;

import lombok.Data;

@Data
public class Legalities{
    public String standard;
    public String future;
    public String historic;
    public String timeless;
    public String gladiator;
    public String pioneer;
    public String explorer;
    public String modern;
    public String legacy;
    public String pauper;
    public String vintage;
    public String penny;
    public String commander;
    public String oathbreaker;
    public String standardbrawl;
    public String brawl;
    public String alchemy;
    public String paupercommander;
    public String duel;
    public String oldschool;
    public String premodern;
    public String predh;

    public String[] values(){
        return new String[]{standard, future, historic, timeless, gladiator, pioneer, explorer, modern, legacy, pauper, vintage, penny, commander, oathbreaker, standardbrawl, brawl, alchemy, paupercommander, duel, oldschool, premodern, predh};
    }
}