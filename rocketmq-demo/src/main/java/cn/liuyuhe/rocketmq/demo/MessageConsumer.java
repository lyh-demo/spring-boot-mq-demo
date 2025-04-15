package cn.liuyuhe.rocketmq.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * @author lyh
 * @date 2025/04/15
 **/

@Component
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "${rocketmq.consumer.topic}", consumerGroup = "${rocketmq.consumer.group}")
public class MessageConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("receiveMessage: " + message);
    }
}
