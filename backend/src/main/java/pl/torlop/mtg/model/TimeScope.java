package pl.torlop.mtg.model;

import java.util.List;

public interface TimeScope {
    String LAST_7_DAYS = "LAST_7_DAYS";
    String LAST_30_DAYS = "LAST_30_DAYS";
    String LAST_90_DAYS = "LAST_90_DAYS";
    String LAST_365_DAYS = "LAST_365_DAYS";
    String ALL = "ALL";
    List<String> TIME_SCOPES = List.of(LAST_7_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_365_DAYS, ALL);
}
