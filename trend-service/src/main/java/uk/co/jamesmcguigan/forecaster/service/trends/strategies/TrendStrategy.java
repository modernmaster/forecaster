package uk.co.jamesmcguigan.forecaster.service.trends.strategies;

import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Map;

public interface TrendStrategy {
    Trend process(Stock stock, Map<String, String> params);
}
