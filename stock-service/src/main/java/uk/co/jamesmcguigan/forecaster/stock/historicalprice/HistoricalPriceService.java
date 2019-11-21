package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.repository.StockRepository;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

@Service
@AllArgsConstructor
public class HistoricalPriceService {

    private static final String SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION = "Symbol not persisted in stock collection";
    private static final String SYMBOL_IS_EMPTY = "Symbol is empty";
    private StockRepository stockRepository;

    public void createHistoricalPriceEvent(String symbol) {
        //call out to data acquisition service as a POST
    }

    public void updatePrice(String symbol, List<Price> prices) throws IllegalArgumentException {
        if (symbol.isEmpty()) {
            throw new IllegalArgumentException(SYMBOL_IS_EMPTY);
        }
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            throw new IllegalArgumentException(SYMBOL_NOT_PERSISTED_IN_STOCK_COLLECTION);
        }
        stock.setHistoricalPrices(prices);
        stockRepository.save(stock);
    }

}
