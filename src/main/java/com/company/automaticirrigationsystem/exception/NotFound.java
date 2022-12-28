package com.company.automaticirrigationsystem.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFound extends BusinessException {

    public NotFound(Class<?> entityName) {
        super("Not Found " + entityName.getSimpleName());
        log.error("Not Found {} Entity", entityName.getSimpleName());
    }

}