package com.company.automaticirrigationsystem.event;

import com.company.automaticirrigationsystem.exception.RabbitMQConnectionError;
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
import java.util.Objects;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SendIrrigationRequestEvent {

    public static final String OUTPUT_EVENT_NAME = "sendIrrigationRequestEvent-out-0";

    private final IrrigationLogService irrigationLogService;
    private final PlotService plotService;
    private final StreamBridge streamBridge;


    @Bean
    public Consumer<Long> sendIrrigationRequestEventListener() {
        return plotId -> {

            log.debug("Consuming sendIrrigationRequestEvent with PlotId={}", plotId);

            plotService.findByIdAndLoadSlots(plotId).ifPresentOrElse(
                    plot -> {

                        if (Objects.nonNull(plot.getSlots()) && !plot.getSlots().isEmpty()) {
                            irrigationLogService.irrigationAllAndLog(plot.getSlots());
                        }

                        publisher(plot);
                    },

                    () -> log.debug("Not Found Plot with Id={} - Stopping republishing this event instance", plotId)
            );

        };
    }

    public void publisher(Plot plot) {
        log.debug("Publishing sendIrrigationRequestEventListener Event with daley={}", plot.getIrrigationEvery());
        Map<String, Object> headers = Map.of(MessageProperties.X_DELAY, plot.getIrrigationEvery());

        try {
            streamBridge.send(
                    OUTPUT_EVENT_NAME,
                    new GenericMessage<>(plot.getId(), headers)
            );
        } catch (Exception e) {
            throw new RabbitMQConnectionError(e);
        }
    }


}
