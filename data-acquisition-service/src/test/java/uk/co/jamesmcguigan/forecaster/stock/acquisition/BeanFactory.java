package uk.co.jamesmcguigan.forecaster.stock.acquisition;

public class BeanFactory {
//    @Bean
//    public SimpleMessageListenerContainer legacyPojoListener(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames(MAPPED_QUEUE);
//        MessageListenerAdapter messageListener = new MessageListenerAdapter(new Object() {
//
//            @SuppressWarnings("unused")
//            public void handleMessage(Object object) {
//                System.out.println("Got a " + object);
//                ApplicationIntegrationTests.latch.countDown();
//            }
//
//        });
//        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
//        jsonConverter.setClassMapper(classMapper());
//        messageListener.setMessageConverter(jsonConverter);
//        container.setMessageListener(messageListener);
//        return container;
//    }
//
//    @Bean
//    public DefaultClassMapper classMapper() {
//        DefaultClassMapper classMapper = new DefaultClassMapper();
//        Map<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("foo", Foo.class);
//        idClassMapping.put("bar", Bar.class);
//        classMapper.setIdClassMapping(idClassMapping);
//        return classMapper;
//    }
//
//    @Bean
//    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(jsonConverter());
//        return template;
//    }
//
//    @Bean
//    public MessageConverter jsonConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public Queue inferredFoo() {
//        return new AnonymousQueue(() -> INFERRED_FOO_QUEUE);
//    }
//
//    @Bean
//    public Queue inferredBar() {
//        return new AnonymousQueue(() -> INFERRED_BAR_QUEUE);
//    }
//
//    @Bean
//    public Queue convertAndReceive() {
//        return new Queue(RECEIVE_AND_CONVERT_QUEUE);
//    }
//
//    @Bean
//    public Queue mapped() {
//        return new AnonymousQueue(() -> MAPPED_QUEUE);
//    }

}
