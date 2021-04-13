package uk.co.jamesmcguigan.forecaster.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.jamesmcguigan.forecaster.historicalprice.HistoricalPriceExecutorService;
import uk.co.jamesmcguigan.forecaster.historicalprice.HistoricalService;

@Configuration
public class HistoricalServiceConfig {
    @Bean
    public HistoricalService historicalService() {
        return new HistoricalPriceExecutorService();
    }
}