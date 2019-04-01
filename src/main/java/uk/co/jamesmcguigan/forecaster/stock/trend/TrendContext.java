package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Map;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.trend.strategies.TrendStrategy;

@Service
public class TrendContext {

    private TrendStrategy trendStrategy;

    public Trend process(Stock stock, Map<String, String> options) {
        return trendStrategy.process(stock,options);
    }

    public void setTrendStrategy(TrendStrategy trendStrategy) {
        this.trendStrategy = trendStrategy;
    }

}
