package com.eazybytes.accounts.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // Queue, Exchange, Routing Key 설정
    public static final String QUEUE_NAME = "account-service-queue";
    public static final String EXCHANGE_NAME = "account-service-exchange";
    public static final String ROUTING_KEY = "account-service-routing-key";

    // Queue 생성
    @Bean
    public Queue accountQueue() {
        return new Queue(QUEUE_NAME, true); // durable = true (재부팅 시에도 유지)
    }

    // Exchange 생성 (DirectExchange 사용)
    @Bean
    public DirectExchange accountExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Queue와 Exchange를 Routing Key로 바인딩
    @Bean
    public Binding accountBinding(Queue accountQueue, DirectExchange accountExchange) {
        return BindingBuilder.bind(accountQueue).to(accountExchange).with(ROUTING_KEY);
    }
}