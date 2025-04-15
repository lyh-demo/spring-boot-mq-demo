package cn.liuyuhe.kafka.demo;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author lyh
 * @date 2025/04/15
 **/

@KafkaListener(id = KafkaConstants.GROUP_ID, topics = KafkaConstants.TOPIC_NAME)
@Component
public class MessageConsumer {

    @KafkaHandler
    public void receiveMessage(String message) {
        System.out.println("receiveMessage: " + message);
    }
}
