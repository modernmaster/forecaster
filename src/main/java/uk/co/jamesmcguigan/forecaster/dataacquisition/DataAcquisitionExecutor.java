package uk.co.jamesmcguigan.forecaster.dataacquisition;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class DataAcquisitionExecutor {

    public Set<Future<DataAcquisitionResponse>> execute(List<Stock> stocks, HistoricalPriceRequest callable) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Set<Future<DataAcquisitionResponse>> responses = new HashSet<>();
        stocks.forEach(
                (stock) -> {
                    Future<DataAcquisitionResponse> scrapeResponse = executorService.submit(callable.create(stock.getSymbol()));
                    responses.add(scrapeResponse);
                });
        executorService.shutdown();
        callable.saveResponses(responses);
        return responses;
    }
}
