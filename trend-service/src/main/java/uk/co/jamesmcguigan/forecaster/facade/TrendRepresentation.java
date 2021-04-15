package uk.co.jamesmcguigan.forecaster.facade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Map;

@NoArgsConstructor
@RequiredArgsConstructor
public class TrendRepresentation {
    @NonNull
    @JsonProperty
    private Map<String, Trend> trends;
}
