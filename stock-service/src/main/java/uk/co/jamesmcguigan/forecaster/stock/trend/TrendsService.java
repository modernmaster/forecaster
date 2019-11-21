package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

@Service
@AllArgsConstructor
public class TrendsService {

    private static final String SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION = "Symbol not persisted in stock collection";
    private static final String SYMBOL_IS_EMPTY = "Symbol is empty";
    private StockRepository stockRepository;

    public void createTrendEvent(String symbol) {
        //Send message to controller
    }

    public void updateTrend(String symbol, Map<String, Trend> trends) throws IllegalArgumentException {
        if (symbol.isEmpty()) {
            throw new IllegalArgumentException(SYMBOL_IS_EMPTY);
        }
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            throw new IllegalArgumentException(SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION);
        }
        stock.setTrends(trends);
        stockRepository.save(stock);
    }
}