package cn.liuyuhe.rabbitmq.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.liuyuhe.rabbitmq.demo.RabbitMQConstants.QUEUE_NAME;

/**
 * @author lyh
 * @date 2025/04/15
 **/

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
}
