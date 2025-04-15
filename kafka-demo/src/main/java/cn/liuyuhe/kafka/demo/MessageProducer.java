package cn.liuyuhe.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lyh
 * @date 2025/04/15
 **/

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(KafkaConstants.TOPIC_NAME, message);
        System.out.println("sendMessage: " + message);
    }
}
