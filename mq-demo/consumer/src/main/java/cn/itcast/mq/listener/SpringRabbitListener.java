package cn.itcast.mq.listener;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Component
public class SpringRabbitListener {
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueue(String msg){
//        System.out.println(msg);
//    }
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的消息：" + msg + LocalTime.now());
        Thread.sleep(20);
    }
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2接收到的消息：" + msg + LocalTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "sa.queue1")
    public void listenSimpleQueue11(String msg) throws InterruptedException {
        System.out.println("消费者接收到sa.queue1的消息：" + msg + LocalTime.now());
        Thread.sleep(20);
    }
    @RabbitListener(queues = "sa.queue2")
    public void listenSimpleQueue22(String msg) throws InterruptedException {
        System.err.println("消费者2接收到sa.queue2的消息：" + msg + LocalTime.now());
        Thread.sleep(200);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "com.sa.dir",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}
    ))
    public void ListenDirectQueue1(String msg){
        System.err.println("消费者接收direct.queue1到的消息：" + msg + LocalTime.now());
    }

    /**
     * 队列绑定交换机，通过key达到交换机可以选择消息队列分发数据的功能
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "com.sa.dir",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void ListenDirectQueue2(String msg){
        System.err.println("消费者接收到direct.queue2的消息：" + msg + LocalTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "sa.topic",type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopQueue1(String msg){
        System.err.println("消费者接收到topic.queue1的消息：" + msg + LocalTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "sa.topic",type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopQueue2(String msg){
        System.err.println("消费者接收到topic.queue2的消息：" + msg + LocalTime.now());
    }

    @RabbitListener(queues = "object.queue")
    public void ListenObjectQueue(Map<String,Object> map){
        System.out.println("接收到object.queue的消息：" + map);
    }
}
