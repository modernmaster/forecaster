package uk.co.jamesmcguigan.forecaster.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePrice;

@Component
@Slf4j
class TrendsMessageProducer {

    private static final String UNABLE_TO_SEND_MESSAGE_ITEM_ID_S_DUE_TO_S = "Unable to send message item id %s due to : %s";
    private static final String SENT_MESSAGE_FOR_ITEM_ID_WITH_OFFSET = "Sent message for item id {} with offset {}";
    private static final String SENDING_MARKET_REQUEST_FOR_ITEM_ID_TO_QUEUE = "Sending market request for item id {} to queue";

    @Autowired
    private KafkaTemplate<String, LivePrice> trendsKafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    public void sendMessage(LivePrice livePrice) {
        log.debug(SENDING_MARKET_REQUEST_FOR_ITEM_ID_TO_QUEUE, livePrice.getSymbol());
        ListenableFuture<SendResult<String, LivePrice>> future = trendsKafkaTemplate.send(topicName, livePrice);

        future.addCallback(new ListenableFutureCallback<SendResult<String, LivePrice>>() {

            @Override
            public void onSuccess(SendResult<String, LivePrice> result) {
                log.debug(SENT_MESSAGE_FOR_ITEM_ID_WITH_OFFSET, livePrice.getSymbol(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format(UNABLE_TO_SEND_MESSAGE_ITEM_ID_S_DUE_TO_S, livePrice.getSymbol(), ex.getMessage()), ex);
            }
        });
    }

}
