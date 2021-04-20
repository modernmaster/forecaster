package uk.co.jamesmcguigan.forecaster.historicalprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.facade.StockServiceClient;
import uk.co.jamesmcguigan.forecaster.facade.request.HistoricalPriceRepresentationTransformer;
import uk.co.jamesmcguigan.forecaster.facade.request.RequestFacade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HistoricalPriceExecutorService implements HistoricalService {

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    @Autowired
    private StockServiceClient stockServiceClient;
    @Autowired
    private HistoricalPriceRepresentationTransformer historicalPriceTransformer;

    @Override
    public void submitHistoricalPriceRequest() {
        //get symbols
        String symbol = "lon:boo";
        RequestFacade requestFacade = new RequestFacade(symbol, stockServiceClient, historicalPriceTransformer);
        executor.submit(requestFacade);
    }

    @Override
    public void submitHistoricalPriceRequest(String symbol) {
        RequestFacade requestFacade = new RequestFacade(symbol, stockServiceClient, historicalPriceTransformer);
        executor.submit(requestFacade);
    }

}
