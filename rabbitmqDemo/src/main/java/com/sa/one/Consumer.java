package com.sa.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者-接收消息
 */
public class Consumer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) {
        // 创建链接工程
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.246.128");
        factory.setUsername("root");
        factory.setPassword("123456");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            /**
             * 消费者消费队列
             * 参数列表
             * 1.消费哪个队列
             * 2.消费成功后是否要自动应答 ture代表自动应答，false则不自动应答
             * 3.消费者成功消费的回调
             * 4.消费者取消消费的回调
             */
            // 声明  接收消息的回调
            DeliverCallback deliverCallback = (consumerTag,message) -> {
              // message代表接收到的消息
                System.out.println("message = " + message);
                System.out.println(new String(message.getBody()));
            };
            // 取消消息的回调
            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("消费消息被中断");
                System.out.println(consumerTag);
            };
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
