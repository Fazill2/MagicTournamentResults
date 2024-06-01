package pl.torlop.mtg.model;

import java.util.List;

public interface Formats {
    String MODERN = "Modern";
    String STANDARD = "Standard";
    String LEGACY = "Legacy";
    String VINTAGE = "Vintage";
    String PAUPER = "Pauper";
    String PIONEER = "Pioneer";
    List<String> formats = List.of(MODERN, STANDARD, LEGACY, VINTAGE, PAUPER, PIONEER);
}
