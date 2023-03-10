package com.company.automaticirrigationsystem;

import com.company.automaticirrigationsystem.event.FailureIrrigationLogEvent;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
import com.company.automaticirrigationsystem.repository.SlotRepository;
import com.company.automaticirrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final PlotService plotService;
    private final SlotRepository slotRepository;
    private final IrrigationLogRepository irrigationLogRepository;
    private final FailureIrrigationLogEvent failureIrrigationLogEvent;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var ms = (int) TimeUnit.MINUTES.toMillis(10);
        var every = (int) TimeUnit.HOURS.toMillis(12);

        var plotList = List.of(
                Plot.builder().amountWater(1f).irrigationEvery(60000).waitBeforeCloseSlots(ms).build(),
                Plot.builder().amountWater(1f).irrigationEvery(every).waitBeforeCloseSlots(ms).build(),
                Plot.builder().amountWater(1f).irrigationEvery(every).waitBeforeCloseSlots(ms).build(),
                Plot.builder().amountWater(1f).irrigationEvery(every).waitBeforeCloseSlots(ms).build()
        );

        plotList = plotList.stream().map(plotService::create).toList();

        log.warn("Plot data loaded - Dump Data");

        var slotList = List.of(
                Slot.builder().plot(plotList.get(0)).build(),
                Slot.builder().plot(plotList.get(1)).build(),
                Slot.builder().plot(plotList.get(2)).build(),
                Slot.builder().plot(plotList.get(3)).build()
        );

        // slotList = slotList.stream().map();
        slotList = slotRepository.saveAll(slotList);

        log.warn("Slot data loaded - Dump Data");

        var irrigationLogList = List.of(
                IrrigationLog.builder().slot(slotList.get(1)).status(SlotStatus.COMPETE_OPEN_REQUEST).build(),
                IrrigationLog.builder().slot(slotList.get(2)).status(SlotStatus.COMPETE_OPEN_REQUEST).build(),
                IrrigationLog.builder().slot(slotList.get(3)).status(SlotStatus.COMPETE_OPEN_REQUEST).build(),
                IrrigationLog.builder().slot(slotList.get(0)).status(SlotStatus.COMPETE_OPEN_REQUEST).build()
        );

        irrigationLogList = irrigationLogRepository.saveAll(irrigationLogList);

        log.warn("irrigationLog data loaded - Dump Data");

        failureIrrigationLogEvent.publishing(
                        1L,
                        (int) TimeUnit.MINUTES.toMillis(1)
        );

    }

}
