package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IrrigationLogService {

    private final IrrigationLogRepository irrigationLogRepository;
    private final SlotService slotService;
    private final IotSlotService iotSlotService;

    public List<IrrigationLog> findAll() {
        return irrigationLogRepository.findAll();
    }

    public IrrigationLog findById(@NotNull Long id) {
        log.debug("Retrieving the IrrigationLog entity with ID={} from the datastore.", id);
        return irrigationLogRepository.findById(id)
                .orElseThrow(() -> new NotFound(IrrigationLog.class));
    }

    public IrrigationLog saveIrrigationLog(@NotNull IrrigationLog irrigationLog) {
        log.debug("storing new IrrigationLog entity to datastore and status={}", irrigationLog.getStatus());
        irrigationLog.setId(null);

        irrigationLog = irrigationLogRepository.save(irrigationLog);
        return irrigationLog;
    }

    public IrrigationLog saveIrrigationLogAndFindSlot(@NotNull IrrigationLog irrigationLog) {
        irrigationLog.setSlot(slotService.findById(irrigationLog.getSlot().getId()));

        return saveIrrigationLog(irrigationLog);
    }

    public void irrigationAllAndLog(List<Slot> slots) {

        log.debug("irrigation Plot with id={}", slots.get(0).getPlot().getId());

        List<IrrigationLog> irrigationLogs = slots.stream().map(slot -> {
            IrrigationLog irrigationLog = IrrigationLog.builder()
                    .slot(slot)
                    .build();

            boolean requestStatus = iotSlotService.irrigationWithRetry(slot, slot.getPlot().getRetryCallLimit());
            irrigationLog.setStatus(mappingIotRequestStatusToSlotStatus(requestStatus));
            return irrigationLog;

        }).toList();

        irrigationLogRepository.saveAll(irrigationLogs);
    }

    public SlotStatus mappingIotRequestStatusToSlotStatus(boolean requestStatus) {
        return requestStatus
                ? SlotStatus.COMPETE_OPEN_REQUEST
                : SlotStatus.ERROR_OPEN_REQUEST;
    }


}
