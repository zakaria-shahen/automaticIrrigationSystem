package com.company.automaticirrigationsystem.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SlotStatus {

    /**
     *  Successfully sending irrigation request
     */
    COMPETE_OPEN_REQUEST,

    /**
     *  Irrigation completed successfully
     */
    COMPETE_IRRIGATION,

    /**
     * Failed to send the irrigation request (e.g. connection problem)
     */
    ERROR_OPEN_REQUEST,

    /**
     * Failure to complete irrigation (e.g. Problems with IoT devices)
     */
    ERROR_IRRIGATION;

    @JsonCreator
    public static SlotStatus convertToEnum(String value) {
        return SlotStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String nameLowerCase() {
        return this.name().toLowerCase();
    }

}
