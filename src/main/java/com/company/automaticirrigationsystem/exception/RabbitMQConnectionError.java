package com.company.automaticirrigationsystem.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMQConnectionError extends BusinessException {

    public RabbitMQConnectionError(Exception ex) {
        super("Error connecting to a third party service");
        log.error("Rabbit Error: {}", ex.getMessage());

    }
}
