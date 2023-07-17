package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    // com.sa 声明交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("com.sa");
    }

    // sa.queue1 声明队列1
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("sa.queue1");
    }

    // sa.queue1 声明队列2
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("sa.queue2");
    }

    // 绑定队列1到交换机
    @Bean
    public Binding fanoutBinding1(@Autowired Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue1)
                .to(fanoutExchange);
    }

    // 绑定队列1到交换机2
    @Bean
    public Binding fanoutBinding2(@Autowired Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(fanoutQueue2)
                .to(fanoutExchange);
    }

    /**
     * 声明一个队列
     * @return
     */
    @Bean
    public Queue objectQueue(){
        return new Queue("object.queue");
    }

}
