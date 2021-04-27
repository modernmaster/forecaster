package uk.co.jamesmcguigan.forecaster.service.trends;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import uk.co.jamesmcguigan.forecaster.facade.StockApiClient;
import uk.co.jamesmcguigan.forecaster.facade.TrendRepresentation;
import uk.co.jamesmcguigan.forecaster.service.trends.strategies.MovingAverageStrategy;
import uk.co.jamesmcguigan.forecaster.service.trends.strategies.RelativeStrengthIndicatorStrategy;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternEvent;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Map;

import static uk.co.jamesmcguigan.forecaster.service.trends.strategies.MovingAverageStrategy.MOVING_AVERAGE_DURATION;
import static uk.co.jamesmcguigan.forecaster.service.trends.strategies.RelativeStrengthIndicatorStrategy.RSI;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrendEngine implements TrendEngineService {

    private static final String CALLING_OUT_TO_STOCK_SERVICE_TO_SAVE_TRENDS_FOR = "Calling out to stock service to save trends for {}";
    private static final String TREND_SAVED_FOR = "Trend saved for: {}";
    private static final String CALLING_OUT_TO_STOCK_API_FOR = "Calling out to stock api for {}";
    private static final String NO_HISTORICAL_PRICES_FOR_STOCK = "No historical prices for stock {}";
    private static final String TRENDS_HAVE_BEEN_IDENTIFIED_FOR = "{} trends have been identified for {}";
    private final TrendContext trendContext;
    private final MovingAverageStrategy movingAverageStrategy;
    private final RelativeStrengthIndicatorStrategy relativeStrengthIndicatorStrategy;
    private final StockApiClient stockApiClient;
    private final PatternMessageProducer patternMessageProducer;

    @Override
    public void processTrendsForPriceEvent(LivePriceEvent livePriceEvent) {
        log.debug(CALLING_OUT_TO_STOCK_API_FOR, livePriceEvent.getSymbol());
        Stock stock = stockApiClient.get(livePriceEvent.getSymbol());
        assert stock != null;
        if (CollectionUtils.isEmpty(stock.getHistoricalPrices())) {
            log.debug(NO_HISTORICAL_PRICES_FOR_STOCK, stock.getSymbol());
            return;
        }
        updateStockWith(stock, livePriceEvent);
        stock.setTrends(Maps.newHashMap());
        processMATrend(stock, 9);
        processMATrend(stock, 20);
        processMATrend(stock, 50);
        processMATrend(stock, 200);
        processRSI(stock, 14);
        log.debug(TRENDS_HAVE_BEEN_IDENTIFIED_FOR, stock.getTrends().size(), stock.getSymbol());
        //save to local table?
        log.debug(CALLING_OUT_TO_STOCK_SERVICE_TO_SAVE_TRENDS_FOR, stock.getSymbol());
        stockApiClient.patch(stock.getSymbol(), new TrendRepresentation(stock.getTrends()));
        log.debug(TREND_SAVED_FOR, stock.getSymbol());
        PatternEvent patternEvent = PatternEvent.builder()
                .symbol(livePriceEvent.getSymbol())
                .trendList(stock.getTrends())
                .price(livePriceEvent.getPrice())
//                .historicalPriceEvent()
                .build();
        patternMessageProducer.sendMessage(patternEvent);
    }

    private void updateStockWith(Stock stock, LivePriceEvent livePriceEvent) {
        stock.setPrice(livePriceEvent.getPrice());
        stock.setVolume(livePriceEvent.getVolume());
    }

//    private static final String SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION = "Symbol not persisted in stock collection";
//    private static final String SYMBOL_IS_EMPTY = "Symbol is empty";
//    private StockRepository stockRepository;
//
//    public void createTrendEvent(String symbol) {
//        //Send message to controller
//    }
//
//    public void updateTrend(String symbol, Map<String, Trend> uk.co.jamesmcguigan.forecaster.service.trends) throws IllegalArgumentException {
//        if (symbol.isEmpty()) {
//            throw new IllegalArgumentException(SYMBOL_IS_EMPTY);
//        }
//        Stock stock = stockRepository.findBySymbol(symbol);
//        if (stock == null) {
//            throw new IllegalArgumentException(SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION);
//        }
//        stock.setTrends(uk.co.jamesmcguigan.forecaster.service.trends);
//        stockRepository.save(stock);
//    }

    private void processRSI(Stock stock, Integer duration) {
        trendContext.setTrendStrategy(relativeStrengthIndicatorStrategy);
        Map<String, String> options = Maps.newHashMap();
        options.put(RSI, duration.toString());
        Trend trend = trendContext.process(stock, options);
        stock.getTrends().put(RSI + "_" + duration, trend);
    }

    private void processMATrend(Stock stock, Integer duration) {
        trendContext.setTrendStrategy(movingAverageStrategy);
        Map<String, String> options = Maps.newHashMap();
        options.put(MOVING_AVERAGE_DURATION, duration.toString());
        Trend trend = trendContext.process(stock, options);
        stock.getTrends().put(MOVING_AVERAGE_DURATION + "_" + duration, trend);
    }
}
