package com.eazybytes.accounts.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.eazybytes.accounts.service.IRabbitMqProducer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class RabbitMqProducer implements IRabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("account-service-exchange", "account-service-routing-key", message);
        System.out.println("Sent message: " + message);
    }
}


