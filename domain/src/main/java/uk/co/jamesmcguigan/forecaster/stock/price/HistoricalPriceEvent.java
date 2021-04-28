package uk.co.jamesmcguigan.forecaster.stock.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalPriceEvent {
    private String symbol;
    private List<HistoricalPrice> historicalPriceList;
}
