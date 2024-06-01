package pl.torlop.mtg.model;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeScope {
    String LAST_7_DAYS = "LAST_7_DAYS";
    String LAST_30_DAYS = "LAST_30_DAYS";
    String LAST_90_DAYS = "LAST_90_DAYS";
    String LAST_365_DAYS = "LAST_365_DAYS";
    String ALL = "ALL";
    List<String> TIME_SCOPES = List.of(LAST_7_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_365_DAYS, ALL);

    static LocalDateTime getStartDateForTimeScope(String timeScope) {
        return switch (timeScope) {
            case LAST_7_DAYS -> LocalDateTime.now().minusDays(7);
            case LAST_30_DAYS -> LocalDateTime.now().minusDays(30);
            case LAST_90_DAYS -> LocalDateTime.now().minusDays(90);
            case LAST_365_DAYS -> LocalDateTime.now().minusDays(365);
            case ALL -> LocalDateTime.of(1990, 1, 1, 0, 0);
            default -> throw new IllegalArgumentException("Invalid time scope: " + timeScope);
        };
    }
}
