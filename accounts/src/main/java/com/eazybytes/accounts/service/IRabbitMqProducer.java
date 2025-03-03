package com.eazybytes.accounts.service;

public interface IRabbitMqProducer {
    void sendMessage(String message);
}
