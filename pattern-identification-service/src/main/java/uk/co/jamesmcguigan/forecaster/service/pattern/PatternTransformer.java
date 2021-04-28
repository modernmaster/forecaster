package uk.co.jamesmcguigan.forecaster.service.pattern;

import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.pattern.IntervalWindow;
import uk.co.jamesmcguigan.forecaster.stock.pattern.Pattern;

@Component
public class PatternTransformer {

    public Pattern transform(PatternTransaction patternTransaction) {
        return Pattern.builder()
                .patternType(patternTransaction.getPattern())
                .targetPrice(patternTransaction.getTargetPrice().get())
                .identifiedPrice(patternTransaction.getIdentifiedPrice())
                .window(IntervalWindow.fromValue(patternTransaction.getIntervalWindow()))
                .dateTimeCreated(patternTransaction.getCreatedDateTime())
                .build();
    }
}
