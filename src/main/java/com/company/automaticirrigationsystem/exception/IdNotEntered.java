package com.company.automaticirrigationsystem.exception;

public class IdNotEntered extends BusinessException {
    public IdNotEntered() {
        super("The id was not entered in your the request");
    }
}
