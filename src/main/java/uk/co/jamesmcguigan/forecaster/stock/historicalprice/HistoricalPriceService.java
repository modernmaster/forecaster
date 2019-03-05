package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionExecutor;
import uk.co.jamesmcguigan.forecaster.stock.Stock;
import uk.co.jamesmcguigan.forecaster.stock.StockRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class HistoricalPriceService implements HistoricalPrice {

    HistoricalPriceRequest historicalPriceRequest;
    StockRepository stockRepository;
    DataAcquisitionExecutor dataAcquisitionExecutor;

    @Override
    public void update() {
        List<Stock> stocks = Lists.newArrayList(stockRepository.findAll());
        dataAcquisitionExecutor.execute(stocks, historicalPriceRequest);
    }

    @Override
    public void update(String symbol) {
        List<Stock> stocks = Lists.newArrayList(stockRepository.findBySymbol(symbol));
        dataAcquisitionExecutor.execute(stocks, historicalPriceRequest);
    }
}
