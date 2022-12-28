package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.event.FailureIrrigationLogEvent;
import com.company.automaticirrigationsystem.model.Slot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IotSlotService {

    private final FailureIrrigationLogEvent failureIrrigationLogEvent;

    public IotSlotService(@Lazy FailureIrrigationLogEvent failureIrrigationLogEvent) {
        this.failureIrrigationLogEvent = failureIrrigationLogEvent;
    }

    public Boolean irrigation(Slot slot) {

        log.debug("sending irrigation request to IoT");
        Boolean invokeIot = true; // Mock

        if (invokeIot.equals(true)) {
            failureIrrigationLogEvent.publishing(
                    slot.getId(),
                    slot.getPlot().getWaitBeforeCloseSlots()
            );
        }

        return invokeIot;
    }
}
