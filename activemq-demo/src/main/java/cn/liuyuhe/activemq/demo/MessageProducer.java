package cn.liuyuhe.activemq.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static cn.liuyuhe.activemq.demo.ActiveMQConstants.QUEUE_NAME;

/**
 * @author lyh
 * @date 2025/04/15
 **/
@Component
public class MessageProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
        System.out.println("sendMessage: " + message);
    }
}
