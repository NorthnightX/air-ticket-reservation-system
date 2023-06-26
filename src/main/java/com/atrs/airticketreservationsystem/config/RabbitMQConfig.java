package com.atrs.airticketreservationsystem.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EMAIL_EXCHANGE = "email_exchange";
    public static final String EMAIL_QUEUE = "email_queue";
    public static final String EMAIL_KEY = "email_key";

    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Binding emailBindingBuilder(){
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(EMAIL_KEY);
    }
}
