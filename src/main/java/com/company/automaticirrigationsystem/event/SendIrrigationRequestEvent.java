package com.company.automaticirrigationsystem.event;

import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.service.IrrigationLogService;
import com.company.automaticirrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.GenericMessage;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SendIrrigationRequestEvent {

    public static final String OUTPUT_EVENT_NAME = "failureIrrigationLogEvent-out-0";

    private final IrrigationLogService irrigationLogService;
    private final PlotService plotService;
    private final StreamBridge streamBridge;


    @Bean
    public Consumer<Long> sendIrrigationRequestEventListener() {
        return plotId -> {

            Plot plot = plotService.findById(plotId);

            irrigationLogService.irrigation(plot.getSlots());

            publisher(plot);

        };
    }

    public void publisher(Plot plot) {
        log.debug("Publishing sendIrrigationRequestEventListener Event with daley={}", plot.getIrrigationEvery());
        Map<String, Object> headers = Map.of(MessageProperties.X_DELAY, plot.getIrrigationEvery());
        streamBridge.send(
                OUTPUT_EVENT_NAME,
                new GenericMessage<>(plot.getId(), headers)
        );
    }


}
