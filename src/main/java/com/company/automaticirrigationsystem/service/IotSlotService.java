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
    private final AlertService alertService;

    public IotSlotService(@Lazy FailureIrrigationLogEvent failureIrrigationLogEvent, AlertService alertService) {
        this.failureIrrigationLogEvent = failureIrrigationLogEvent;
        this.alertService = alertService;
    }

    public Boolean irrigation(Slot slot) {

        log.debug("sending irrigation request to IoT");
        Boolean invokeIot = true; // Mock

        if (invokeIot.equals(true)) {
            failureIrrigationLogEvent.publishing(
                    slot.getId(),
                    slot.getPlot().getWaitBeforeCloseSlots()
            );
        } else {
            log.debug("IoT not available");
        }

        return invokeIot;
    }

    public Boolean irrigationWithRetry(Slot slot, Integer retryBeforeAlert) {

        Boolean requestStatus = irrigation(slot);

        while (requestStatus.equals(false) && retryBeforeAlert > 0) {
            log.debug("retry send irrigation request to IoT");
            retryBeforeAlert--;
            requestStatus = irrigation(slot);
        }

        if (requestStatus.equals(false)) {
            log.debug("sending alert");
            alertService.alert("IoT not available");
        }

        return requestStatus;
    }

}
