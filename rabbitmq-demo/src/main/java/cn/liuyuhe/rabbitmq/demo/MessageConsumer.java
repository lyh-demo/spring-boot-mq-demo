package cn.liuyuhe.rabbitmq.demo;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static cn.liuyuhe.rabbitmq.demo.RabbitMQConstants.QUEUE_NAME;

/**
 * @author lyh
 * @date 2025/04/15
 **/

@RabbitListener(queues = QUEUE_NAME)
@Component
public class MessageConsumer {

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("receiveMessage: " + message);
    }
}
