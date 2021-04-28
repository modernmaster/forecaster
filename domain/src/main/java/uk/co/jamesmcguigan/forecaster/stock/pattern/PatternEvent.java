package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.jamesmcguigan.forecaster.stock.price.HistoricalPriceEvent;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatternEvent {
    private String symbol;
    private Double price;
    private HistoricalPriceEvent historicalPriceEvent;
    private Map<String, Trend> trendList;
}
