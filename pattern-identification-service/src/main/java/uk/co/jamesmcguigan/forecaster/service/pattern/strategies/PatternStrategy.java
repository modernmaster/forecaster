package uk.co.jamesmcguigan.forecaster.service.pattern.strategies;

import uk.co.jamesmcguigan.forecaster.service.pattern.PatternTransaction;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

import java.util.Map;
import java.util.Optional;

public interface PatternStrategy {
    Optional<PatternTransaction> process(Stock stock, Map<String, String> params);
}
