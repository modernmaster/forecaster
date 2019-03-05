package uk.co.jamesmcguigan.forecaster;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uk.co.jamesmcguigan.forecaster.stock.StockLookup;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPrice;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class Application {
    private StockLookup stockLookup;
    private HistoricalPrice historicalPrice;
    private TrendsScheduledTask trendsScheduledTask;
    @Value("${jobs.processHistoricalPricing.enable}")
    private static boolean processHistoricalPricingEnable;
    @Value("${jobs.processCurrentPrices.enable}")
    private static boolean processCurrentPricesEnable;

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
    public void processHistoricalPricing() {
        if (processHistoricalPricingEnable) {
            historicalPrice.update();
        }
    }

    //TODO: refactor out trends scheduled task and place into package.
    @Scheduled(fixedRate = 30000)
    public void processTrends() {

    }
}
