package com.eazybytes.accounts;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    // Queue, Exchange, Routing Key 설정
    public static final String QUEUE_NAME = "account-service-queue";
    public static final String EXCHANGE_NAME = "account-service-exchange";
    public static final String ROUTING_KEY = "account-service-routing-key";

    // @Bean
    // public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    //     SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    //     factory.setConnectionFactory(connectionFactory);
    //     factory.setAutoStartup(true);  // 🔹 Bean이 안전하게 시작됨
    //     return factory;
    // }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

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