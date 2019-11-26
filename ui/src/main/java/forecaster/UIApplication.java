package forecaster;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@RequiredArgsConstructor
public class UIApplication {
//    @Value("${jobs.processHistoricalPricing.enable}")
//    private boolean processHistoricalPricingEnable;
//    @Value("${jobs.processCurrentPrices.enable}")
//    private boolean processCurrentPricesEnable;

    public static final Logger logger = LoggerFactory.getLogger("uk.co.jamesmcguigan.forecaster");

    public static void main(String[] args) {
        SpringApplication.run(UIApplication.class, args);
    }

    @Scheduled(fixedRate = 30001)
    public void processCurrentPrices() {
//        if (processCurrentPricesEnable) {
//            stockLookup.updateStocksAndNotifyAllClients();
//        }
//        if (processHistoricalPricingEnable) {
//            historicalPrice.update();
////        process patterns -> and notify
//        }
//        trendEngine.processTrendsForAllStocks();
    }

}
