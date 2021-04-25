package uk.co.jamesmcguigan.forecaster.historicalprice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.co.jamesmcguigan.forecaster.facade.StockServiceClient;
import uk.co.jamesmcguigan.forecaster.facade.request.HistoricalPriceRepresentationTransformer;
import uk.co.jamesmcguigan.forecaster.facade.request.RequestFacade;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class HistoricalPriceExecutorService implements HistoricalService {

    private static final String HISTORICAL_PRICE_REQUEST_FOR_ALL_STOCKS_RECEIVED = "Historical price request for all stocks received";
    private static final String CALL_OUT_TO_RETRIEVE_ALL_STOCKS = "Call out to retrieve all stocks";
    private static final String PROJECTION = "projection";
    private static final String STOCK_PROJECTION = "stockProjection";
    private static final String PROCESSING_SYMBOL = "Processing symbol {}";
    private static final String ADDING_HISTORICAL_PRICE_REQUEST_TO_EXECUTOR_SERVICE = "Adding historical price request to executor service";
    private static final String SIZE = "size";
    private static final String PAGE_SIZE = "2000";
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    @Autowired
    private StockServiceClient stockServiceClient;
    @Autowired
    private HistoricalPriceRepresentationTransformer historicalPriceTransformer;

    @Override
    public void submitHistoricalPriceRequest() {
        log.debug(HISTORICAL_PRICE_REQUEST_FOR_ALL_STOCKS_RECEIVED);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add(PROJECTION, STOCK_PROJECTION);
        queryParams.add(SIZE, PAGE_SIZE);
        log.debug(CALL_OUT_TO_RETRIEVE_ALL_STOCKS);
        stockServiceClient.get(queryParams).subscribe(stockSymbols -> {
            Objects.requireNonNull(stockSymbols.getContent()).forEach(stockSymbol -> {
                log.debug(PROCESSING_SYMBOL, stockSymbol.getId());
                RequestFacade requestFacade = new RequestFacade(stockSymbol.getId(), stockServiceClient, historicalPriceTransformer);
                log.debug(ADDING_HISTORICAL_PRICE_REQUEST_TO_EXECUTOR_SERVICE);
                executor.submit(requestFacade);
            });
        });
    }

    @Override
    public void submitHistoricalPriceRequest(String symbol) {
        RequestFacade requestFacade = new RequestFacade(symbol, stockServiceClient, historicalPriceTransformer);
        executor.submit(requestFacade);
    }

}
