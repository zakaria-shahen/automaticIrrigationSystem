package com.company.automaticirrigationsystem.event;

import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.service.AlertService;
import com.company.automaticirrigationsystem.service.IrrigationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FailureIrrigationLogEventConfig {

    @Value("app.WaitBeforeAlert")
    private static Integer waitBeforeAlert = 15;
    public static final String INPUT_EVENT_NAME = "failureIrrigationLogEvent-out-0";

    private final IrrigationLogService irrigationLogService;
    private final StreamBridge bridge;
    private final AlertService alertService;


    @Bean
    public Consumer<Long> failureIrrigationLogEventListener() {
        return slotId -> {
            String message = "The irrigation request has been sent to the IoT, but no callback for completing the process within the designated delay has been received.";

            log.debug(message);
            Slot slot = new Slot(slotId);
            irrigationLogService.create(
                    IrrigationLog.builder()
                            .dateTime(LocalDateTime.now())
                            .slot(slot)
                            .status(SlotStatus.ERROR_IRRIGATION)
                            .details(message)
                            .build()
            );

            alertService.alert(message);
        };
    }

    public void failureIrrigationLogEventPublisher(Long slotID, Integer afterMilliseconds) {

        Integer daley = afterMilliseconds + waitBeforeAlert;

        log.debug("Publishing failureIrrigationLog Event with daley={}", daley);

        Map<String, Object> headers = Map.of(MessageProperties.X_DELAY, 30000);

        bridge.send(
                INPUT_EVENT_NAME,
                new GenericMessage<>(slotID, headers)
        );

    }

}
