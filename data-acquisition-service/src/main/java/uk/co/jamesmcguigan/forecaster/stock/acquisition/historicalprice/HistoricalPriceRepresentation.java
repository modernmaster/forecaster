package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPriceRepresentation {
    @NonNull
    @JsonProperty
    List<PriceRepresentation> historicalPrices;
}
