package uk.co.jamesmcguigan.forecaster.stock.acquisition;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.HistoricalPriceService;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;

@Configuration
public class EventConfiguration {

    static final String QUEUE_PRICE_HISTORICAL = "price.historical";
    private static final String PRICE_HISTORICAL_ROUTING = "price.historical.*";
    static final String TRENDS = "trends.current";
    private static final String TRENDS_ROUTING = "trends.*";
    static final String PRICE_CURRENT = "price.current";
    private static final String PRICE_CURRENT_ROUTING = "price.current.*";
    private static final String EVENT_EXCHANGE = "eventExchange";

    @Autowired
    private HistoricalPriceService historicalPriceService;

    @Autowired
    private JobService jobService;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EVENT_EXCHANGE);
    }

    @Bean
    public Queue trendsQueue() {
        return new Queue(TRENDS, true);
    }

    @Bean
    public Queue priceCurrentQueue() {
        return new Queue(PRICE_CURRENT, true);
    }

    @Bean
    public Queue priceHistoricalQueue() {
        return new Queue(QUEUE_PRICE_HISTORICAL, true);
    }

    @Bean
    public Binding topicBindingRouting(TopicExchange exchange, Queue trendsQueue) {
        return BindingBuilder
                .bind(trendsQueue).to(exchange).with(TRENDS_ROUTING);
    }

    @Bean
    public Binding topicBindingPriceHistorical(TopicExchange exchange, Queue priceHistoricalQueue) {
        return BindingBuilder
                .bind(priceHistoricalQueue).to(exchange).with(PRICE_HISTORICAL_ROUTING);
    }

    @Bean
    public Binding topicBindingPriceCurrent(TopicExchange exchange, Queue priceCurrentQueue) {
        return BindingBuilder
                .bind(priceCurrentQueue).to(exchange).with(PRICE_CURRENT_ROUTING);
    }

    @Bean
    public EventListener eventReceiver(HistoricalPriceService historicalPriceService) {
        return new EventListener(historicalPriceService, jobService);
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }
//    @Bean
//    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }
}