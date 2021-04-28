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
public class PriceEventListener {

    private final TrendEngineService trendEngineService;

    @KafkaListener(topics = "${message.subscribe.topic.name}", groupId = "livePriceEvent", concurrency = "10", containerFactory = "livePriceEventKafkaListenerContainerFactory")
    public void process(LivePriceEvent livePriceEvent) {
        log.debug("Received price event for symbol {}", livePriceEvent.getSymbol());
        trendEngineService.processTrendsForPriceEvent(livePriceEvent);
    }
}
