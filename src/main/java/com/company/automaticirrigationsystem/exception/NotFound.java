package com.company.automaticirrigationsystem.exception;

public class NotFound extends BusinessException {

    public NotFound(Class entityName) {
        super("Not Found " + entityName.getName());
    }
}
