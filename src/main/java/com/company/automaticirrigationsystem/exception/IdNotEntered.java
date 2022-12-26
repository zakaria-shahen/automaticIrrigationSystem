package com.company.automaticirrigationsystem.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdNotEntered extends BusinessException {

    public IdNotEntered() {
        super("The id was not entered in your the request");
        log.error("The id was not entered in your the request");
    }
}
