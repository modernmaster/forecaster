package uk.co.jamesmcguigan.forecaster.service.pattern;

import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.pattern.Pattern;

@Component
public class PatternTransformer {

    public Pattern transform(PatternTransaction patternTransaction) {
        return Pattern.builder()
                .patternType(patternTransaction.getPattern())
                .targetPrice(1d)
                .identifiedPrice(1d)
                .window(1)
                .dateTimeCreated(patternTransaction.getCreatedDateTime())
                .build();
    }
}
