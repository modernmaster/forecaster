package uk.co.jamesmcguigan.forecaster.stock.trend.strategies;

import java.util.Map;

import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

public interface TrendStrategy {
    Trend process(Stock stock, Map<String, String> params);
}
