package uk.co.jamesmcguigan.forecaster.service.trends;

import lombok.Setter;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.service.trends.strategies.TrendStrategy;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Map;

@Service
@Setter
public class TrendContext {

    private TrendStrategy trendStrategy;

    public Trend process(Stock stock, Map<String, String> options) {
        return trendStrategy.process(stock, options);
    }
}
