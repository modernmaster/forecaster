package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

public interface HistoricalPrice {
    void update();

    void update(String symbol);
}
