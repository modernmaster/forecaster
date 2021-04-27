package uk.co.jamesmcguigan.forecaster.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.service.pattern.PatternService;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternEvent;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriceEventListener {

    private final PatternService patternService;

    @KafkaListener(topics = "${message.topic.name}", groupId = "patternEventEvent", concurrency = "10", containerFactory = "livePriceEventKafkaListenerContainerFactory")
    public void process(PatternEvent patternEvent) {
        log.debug("Received price event for symbol {}", patternEvent.getSymbol());
        patternService.processEvent(patternEvent);
    }
}