package uk.co.jamesmcguigan.forecaster.stock;

import java.util.List;

public interface StockLookup {
    Stock getStockBySymbol(String symbol);

    List<Stock> getUpdatedStocks();

    List<Stock> getStocks();

    Stock createStock(String symbol);

    void createStocks();

    abstract void updateStocks();

    void updateStock(Stock stock);

    void updateStock(String id);

    void updateStocksAndNotifyAllClients();
}
