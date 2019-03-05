package uk.co.jamesmcguigan.forecaster.stock;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPriceService;

@Configuration
@AllArgsConstructor
public class StockLookupServiceConfig {

    private final StockTransformer stockTransformer;
    private final StockRepository stockRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final HistoricalPriceService historicalPriceService;

    @Bean
    public StockLookup StockLookupService() {
        WebClient webClient = WebClient.create();
        return new StockLookupService(stockTransformer, webClient, stockRepository, simpMessagingTemplate, historicalPriceService);
    }
}

