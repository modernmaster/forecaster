package uk.co.jamesmcguigan.forecaster.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.service.trends.TrendEngineService;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class HistoricPriceEventListener {

    private final TrendEngineService trendEngineService;

    @KafkaListener(topics = "${message.topic.name}", groupId = "historicalPriceEvent", concurrency = "10")
    public void process(LivePriceEvent priceEvent) {
        log.debug("Received historic price event for symbol {}", "TEST");
        //trendEngineService.processTrendsForPriceEvent(priceEvent);
    }
}
