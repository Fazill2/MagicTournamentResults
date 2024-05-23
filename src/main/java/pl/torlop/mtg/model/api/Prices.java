package pl.torlop.mtg.model.api;

import lombok.Data;

@Data
public class Prices{
    public String usd;
    public Object usd_foil;
    public Object usd_etched;
    public String eur;
    public Object eur_foil;
    public String tix;
}
