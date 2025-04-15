package cn.liuyuhe.activemq.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lyh
 * @date 2025/04/15
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {
    @Autowired
    private MessageProducer producer;

    @Test
    public void testActiveMQ() {
        producer.sendMessage("test");
    }
}
