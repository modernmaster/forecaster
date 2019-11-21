package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobQueueItem;

@AllArgsConstructor
public class CreateHistoricalPriceEvent {

    public static final String PRICE_HISTORICAL_REQUEST = "price.historical.request";
    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public void send(JobQueueItem jobQueueItem) {
        String routingKey = PRICE_HISTORICAL_REQUEST;
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, jobQueueItem);
    }

}
