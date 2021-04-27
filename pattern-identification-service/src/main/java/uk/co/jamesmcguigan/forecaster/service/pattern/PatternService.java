package uk.co.jamesmcguigan.forecaster.service.pattern;

import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternEvent;

public interface PatternService {
    void processEvent(PatternEvent patternEvent);
}
