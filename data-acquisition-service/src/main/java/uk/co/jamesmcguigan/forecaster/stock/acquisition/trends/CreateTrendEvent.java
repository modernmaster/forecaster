package uk.co.jamesmcguigan.forecaster.stock.acquisition.trends;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateTrendEvent {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public void send() {

        String routingKey = "trend.request";
        String symbol = "lon:sxx";
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, symbol);
    }

}
