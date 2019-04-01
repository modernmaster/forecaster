package uk.co.jamesmcguigan.forecaster;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uk.co.jamesmcguigan.forecaster.stock.StockLookup;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.stock.trend.TrendEngine;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
@EnableConfigurationProperties
public class Application {
    private final StockLookup stockLookup;
    private final HistoricalPrice historicalPrice;
    private final TrendEngine trendEngine;
    @Value("${jobs.processHistoricalPricing.enable}")
    private boolean processHistoricalPricingEnable;
    @Value("${jobs.processCurrentPrices.enable}")
    private boolean processCurrentPricesEnable;

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.forecaster");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 30000)
    public void processCurrentPrices() {
        if (processCurrentPricesEnable) {
            stockLookup.updateStocksAndNotifyAllClients();
        }
    }

    @Scheduled(fixedRate = 30000)
    public void processHistoricalPricingAndUpdateTrendsAndPatterns() {
        if (processHistoricalPricingEnable) {
            historicalPrice.update();
            trendEngine.processTrendsForAllStocks();
//        process patterns -> and notify
        }
    }

}
