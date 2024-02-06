package com.ntx.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    //邮件
    public static final String EMAIL_EXCHANGE = "email_exchange";
    public static final String EMAIL_QUEUE = "email_queue";
    public static final String EMAIL_KEY = "email_key";
    //订单
    public static final String ORDER_EXCHANGE = "order_exchange";
    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_KEY = "order_key";

    //邮件

    /**
     * 邮箱交换机
     * @return
     */
    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    /**
     * 绑定关系，将队列绑定到交换机
     * @return
     */
    @Bean
    public Binding emailBindingBuilder(){
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(EMAIL_KEY);
    }

    //订单
    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE);
    }

    @Bean
    public Binding orderBindingBuilder(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_KEY);
    }


}
