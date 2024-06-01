package pl.torlop.mtg.model.aggregation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardCount {
    private String id;
    private Long count;
    private Double averageQuantity;
    private String format;
}
