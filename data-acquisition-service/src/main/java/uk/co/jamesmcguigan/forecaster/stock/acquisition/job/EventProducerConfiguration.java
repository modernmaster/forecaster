package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.CreateHistoricalPriceEvent;

@Configuration
public class EventProducerConfiguration {

    @Bean
    public Exchange eventExchange() {
        //TODO: Environment variable from kubernetes
        return new TopicExchange("eventExchange");
    }

    @Bean
    public CreateHistoricalPriceEvent customerService(RabbitTemplate rabbitTemplate, Exchange eventExchange) {
        return new CreateHistoricalPriceEvent(rabbitTemplate, eventExchange);
    }

}

