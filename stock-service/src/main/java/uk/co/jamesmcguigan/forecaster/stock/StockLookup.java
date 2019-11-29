package uk.co.jamesmcguigan.forecaster.stock;

import java.util.List;

public interface StockLookup {
    List<Stock> getUpdatedStocks();

    List<Stock> getStocks();

    Stock createStock(String symbol);

    void createStocks();

    void updateStocks();

    void updateStock(Stock stock);

    void updateStock(String id);

}
