package uk.co.jamesmcguigan.forecaster.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockScheduledTask {

    private static final String STOCK_LISTING_UPDATED = "Stock listing updated";
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    private static final Logger log = LoggerFactory.getLogger(StockScheduledTask.class);

    @Scheduled(fixedRate = 30000)
    public void updateStockRepository() {
        List<Stock> stockList = stockService.getStocks();
        stockRepository.saveAll(stockList);
        log.info(STOCK_LISTING_UPDATED);
    }
}
