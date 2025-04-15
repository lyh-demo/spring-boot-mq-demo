package cn.liuyuhe.rocketmq.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyh
 * @date 2025/04/15
 **/
@SpringBootApplication
@RestController
public class Application {
    @Autowired
    private MessageProducer messageProducer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        if (message.isEmpty()) {
            return "消息不能为空";
        }
        messageProducer.sendMessage(message);
        return "发送成功";
    }
}
