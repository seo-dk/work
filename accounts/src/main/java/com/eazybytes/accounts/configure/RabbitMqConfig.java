package com.eazybytes.accounts.configure;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "account-service-exchange";
    public static final String ROUTING_KEY = "account-service-routing-key";
    public static final String QUEUE_NAME = "account-service-queue";

    // @Bean
    // public CachingConnectionFactory connectionFactory() {
    //     CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    //     connectionFactory.setHost("192.168.100.221");  // 직접 IP 사용
    //     connectionFactory.setPort(30802);             // NodePort 사용
    //     connectionFactory.setUsername("guest");
    //     connectionFactory.setPassword("guest");
    //     connectionFactory.setVirtualHost("/");
    //     return connectionFactory;
    // }

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
  
    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;
  
    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;
  
    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        return rabbitTemplate;
    }
}