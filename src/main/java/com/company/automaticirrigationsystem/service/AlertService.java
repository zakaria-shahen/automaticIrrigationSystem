package com.company.automaticirrigationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlertService {

    public void alert(String message) {
        log.debug(message);
    }
}
