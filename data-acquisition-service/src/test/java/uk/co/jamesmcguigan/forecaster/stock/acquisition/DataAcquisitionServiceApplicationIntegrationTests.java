package uk.co.jamesmcguigan.forecaster.service.stock.acquisition;

import java.util.concurrent.CountDownLatch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataAcquisitionServiceApplicationIntegrationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplate jsonRabbitTemplate;

    @Autowired
    private AmqpAdmin rabbitAdmin;

    static volatile CountDownLatch latch = new CountDownLatch(2);

    private static Message jsonMessage;

    @Test
    @Ignore
    public void contextLoads() {
    }

//    @Test
//    public void testReceiveAndDeserializeMessage() throws InterruptedException {
//        sendInferredMessages();
//        Foo foo = this.jsonRabbitTemplate.receiveAndConvert(RECEIVE_AND_CONVERT_QUEUE, 10_000,
//                new ParameterizedTypeReference<Foo>() {
//                });
//        assertThat(foo.getFoo(), equalTo("value"));
//        Bar bar = this.jsonRabbitTemplate.receiveAndConvert(RECEIVE_AND_CONVERT_QUEUE, 10_000,
//                new ParameterizedTypeReference<Bar>() {
//                });
//        assertThat(bar.getFoo(), equalTo("value"));
//        this.rabbitAdmin.deleteQueue(RECEIVE_AND_CONVERT_QUEUE);
//    }
//
//    public void sendInferredMessages() throws InterruptedException {
//        String json = "{\"foo\" : \"value\" }";
//        jsonMessage = MessageBuilder.withBody(json.getBytes())
//                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
//                        .build()).build();
//
//        // inferred type
//
//        rabbitTemplate.send(INFERRED_FOO_QUEUE, jsonMessage);
//        rabbitTemplate.send(INFERRED_BAR_QUEUE, jsonMessage);
//        latch.await(10, TimeUnit.SECONDS);
//        // convertAndReceive with type
//        rabbitTemplate.send(RECEIVE_AND_CONVERT_QUEUE, jsonMessage);
//        rabbitTemplate.send(RECEIVE_AND_CONVERT_QUEUE, jsonMessage);
//    }
//
//    @Test
//    public void testSendingMappedQueueMessages() throws InterruptedException {
//        // Mapped type information with legacy POJO listener
//        this.latch = new CountDownLatch(2);
//        jsonMessage.getMessageProperties().setHeader("__TypeId__", "foo");
//        this.rabbitTemplate.send(MAPPED_QUEUE, jsonMessage);
//        jsonMessage.getMessageProperties().setHeader("__TypeId__", "bar");
//        this.rabbitTemplate.send(MAPPED_QUEUE, jsonMessage);
//        this.latch.await(10, TimeUnit.SECONDS);
//    }
//
//    @RabbitListener(queues = INFERRED_FOO_QUEUE)
//    public void listenForAFoo(Foo foo) {
//        assertThat(foo.getFoo(), equalTo("value"));
//        ApplicationIntegrationTests.latch.countDown();
//    }
//
//    @RabbitListener(queues = INFERRED_BAR_QUEUE)
//    public void listenForAFoo(Bar bar) {
//        assertThat(bar.getFoo(), equalTo("value"));
//        ApplicationIntegrationTests.latch.countDown();
//    }

}
