package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.Application;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockRepository;
import uk.co.jamesmcguigan.forecaster.stock.trend.strategies.MovingAverageStrategy;

import static uk.co.jamesmcguigan.forecaster.stock.trend.strategies.MovingAverageStrategy.MOVING_AVERAGE_DURATION;

@Service
@AllArgsConstructor
public class TrendEngine implements TrendEngineService {

    private static final String TREND_SAVED_FOR = "Trend saved for: %s";
    private TrendContext trendContext;
    private MovingAverageStrategy movingAverageStrategy;
    private StockRepository stockRepository;

    @Override
    public void processTrendsForAllStocks() {
        List<Stock> stocks = Lists.newArrayList(stockRepository.findAll());
        stocks.forEach(stock -> {
            processMATrend(stock, 9);
            processMATrend(stock, 20);
            processMATrend(stock, 50);
            processMATrend(stock, 200);
            stockRepository.save(stock);
            Application.logger.info(String.format(TREND_SAVED_FOR, stock.getSymbol()));
        });
    }

    private void processMATrend(Stock stock, Integer duration) {
        trendContext.setTrendStrategy(movingAverageStrategy);
        Map<String, String> options = Maps.newHashMap();
        options.put(MOVING_AVERAGE_DURATION, duration.toString());
        Trend trend = trendContext.process(stock, options);
        if (stock.getTrends() == null) {
            stock.setTrends(Maps.newHashMap());
        }
        stock.getTrends().put(MOVING_AVERAGE_DURATION + "_" + duration, trend);
    }
}
