package uk.co.jamesmcguigan.forecaster.stock;

import java.util.List;

public interface StockLookup {
    List<Stock> getUpdatedStocks();
    List<Stock> getStocks();
    void updateStocks();
    void updateStocksAndNotifyAllClients();
}
