package com.eazybytes.accounts.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("📢 [RabbitMQ] Sent message: " + message);
    }
}
