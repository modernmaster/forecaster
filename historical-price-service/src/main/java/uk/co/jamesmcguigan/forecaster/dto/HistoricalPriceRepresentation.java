package uk.co.jamesmcguigan.forecaster.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import uk.co.jamesmcguigan.forecaster.historicalprice.PriceRepresentation;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPriceRepresentation {
    @NonNull
    @JsonProperty
    List<PriceRepresentation> historicalPrices;
}
