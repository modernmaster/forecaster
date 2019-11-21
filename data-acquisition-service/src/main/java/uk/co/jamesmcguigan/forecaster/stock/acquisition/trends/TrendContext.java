package uk.co.jamesmcguigan.forecaster.stock.acquisition.trends;

import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.trends.strategies.TrendStrategy;

@Service
public class TrendContext {

    private TrendStrategy trendStrategy;

//    public Trend process(Stock stock, Map<String, String> options) {
//        return trendStrategy.process(stock, options);
//    }

    public void setTrendStrategy(TrendStrategy trendStrategy) {
        this.trendStrategy = trendStrategy;
    }

}
