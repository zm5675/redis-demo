package cn.itcast.mq.helloworld;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AmqpTest {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    void test1(){
        String queueName = "simple.queue";
        String message = "hello sa";
        rabbitTemplate.convertAndSend(queueName,message);

    }
    @Test
    void test2() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello sa__";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName,message + i);
            Thread.sleep(20);
        }
    }
    @Test
    void testsendFanoutExchange(){
        // 交换机名称
        String exchangeName = "com.sa";
        // 消息
        String msg = "hello world";
        // 发送
        rabbitTemplate.convertAndSend(exchangeName,"",msg);
    }

    @Test
    void testSendDirectExchange(){
        // 交换机名称
        String exchangeName = "com.sa.dir";
        // 消息
        String msg = "hello world";
        // 发送
        rabbitTemplate.convertAndSend(exchangeName,"red",msg);
    }


    @Test
    void testSendTopicExchange(){
        // 交换机名称
        String exchangeName = "sa.topic";
        // 消息
        String msg = "hello world";

        // 发送
        rabbitTemplate.convertAndSend(exchangeName,"china.news",msg);
    }

    @Test
    void testSendObjectQueue(){
        Map msg = new HashMap();
        msg.put("name","sa");
        msg.put("age",18);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }
}
