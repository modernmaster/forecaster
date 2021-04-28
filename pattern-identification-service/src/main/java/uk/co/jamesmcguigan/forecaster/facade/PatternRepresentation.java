package uk.co.jamesmcguigan.forecaster.facade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import uk.co.jamesmcguigan.forecaster.stock.pattern.Pattern;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PatternRepresentation {
    @NonNull
    @JsonProperty
    private List<Pattern> patterns;
}
