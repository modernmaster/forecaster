package uk.co.jamesmcguigan.forecaster.historicalprice;

public interface HistoricalService {
    void submitHistoricalPriceRequest();

    void submitHistoricalPriceRequest(String symbol);
}
