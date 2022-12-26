package com.company.automaticirrigationsystem;

import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
import com.company.automaticirrigationsystem.repository.SlotRepository;
import com.company.automaticirrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final PlotService plotService;
    private final SlotRepository slotRepository;
    private final IrrigationLogRepository irrigationLogRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var plotList = List.of(
                Plot.builder().amountWater(1f).build(),
                Plot.builder().amountWater(1f).build(),
                Plot.builder().amountWater(1f).build(),
                Plot.builder().amountWater(1f).build()
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
        slotList = (List<Slot>) slotRepository.saveAll(slotList);

        log.warn("Slot data loaded - Dump Data");

        var irrigationLogList = List.of(
                IrrigationLog.builder().slot(slotList.get(0)).build(),
                IrrigationLog.builder().slot(slotList.get(1)).build(),
                IrrigationLog.builder().slot(slotList.get(2)).build(),
                IrrigationLog.builder().slot(slotList.get(3)).build()
        );

        irrigationLogList = irrigationLogRepository.saveAll(irrigationLogList);

        log.warn("irrigationLog data loaded - Dump Data");

    }

}
