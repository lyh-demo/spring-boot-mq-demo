package cn.liuyuhe.rocketmq.demo;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author lyh
 * @date 2025/04/15
 **/

@Component
public class MessageProducer {
    @Value("${rocketmq.consumer.topic}")
    private String topic;
    @Autowired
    private RocketMQTemplate rocketmqTemplate;

    public void sendMessage(String message) {
        Message<String> msg = MessageBuilder.withPayload(message).build();
        rocketmqTemplate.send(topic, msg);
        System.out.println("sendMessage: " + message);
    }
}
