package uk.co.jamesmcguigan.forecaster.service.trends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternEvent;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;

@Component
@Slf4j
class PatternMessageProducer {

    private static final String UNABLE_TO_SEND_MESSAGE_ITEM_ID_S_DUE_TO_S = "Unable to send message item id %s due to : %s";
    private static final String SENT_MESSAGE_FOR_ITEM_ID_WITH_OFFSET = "Sent message for item id {} with offset {}";
    private static final String SENDING_PATTERN_REQUEST_FOR_SYMBOL_TO_QUEUE = "Sending pattern request for symbol {} to queue";

    @Autowired
    private KafkaTemplate<String, PatternEvent> trendsKafkaTemplate;

    @Value(value = "${message.publish.topic.name}")
    private String topicName;

    public void sendMessage(PatternEvent patternEvent) {
        log.debug(SENDING_PATTERN_REQUEST_FOR_SYMBOL_TO_QUEUE, patternEvent.getSymbol());
        ListenableFuture<SendResult<String, PatternEvent>> future = trendsKafkaTemplate.send(topicName, patternEvent);

        future.addCallback(new ListenableFutureCallback<SendResult<String, PatternEvent>>() {

            @Override
            public void onSuccess(SendResult<String, PatternEvent> result) {
                log.debug(SENT_MESSAGE_FOR_ITEM_ID_WITH_OFFSET, patternEvent.getSymbol(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format(UNABLE_TO_SEND_MESSAGE_ITEM_ID_S_DUE_TO_S, patternEvent.getSymbol(), ex.getMessage()), ex);
            }
        });
    }

}
