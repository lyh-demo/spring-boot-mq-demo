package cn.liuyuhe.activemq.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static cn.liuyuhe.activemq.demo.ActiveMQConstants.QUEUE_NAME;

/**
 * @author lyh
 * @date 2025/04/15
 **/
@Component
public class MessageConsumer {
    @JmsListener(destination = QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("receiveMessage: " + message);
    }
}
