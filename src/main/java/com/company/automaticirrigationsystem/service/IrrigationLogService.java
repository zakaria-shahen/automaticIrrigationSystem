package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public IrrigationLog findById(Long id) {
        log.debug("Retrieving the IrrigationLog entity with ID={} from the datastore.", id);
        return irrigationLogRepository.findById(id)
                .orElseThrow(() -> new NotFound(IrrigationLog.class));
    }

    public IrrigationLog create(IrrigationLog irrigationLog, boolean isIot) {
        log.debug("storing new IrrigationLog entity to datastore and status={}", irrigationLog.getStatus());
        irrigationLog.setId(null);
        irrigationLog.setSlot(slotService.findById(irrigationLog.getSlot().getId()));

        handleSlotStatus(irrigationLog, isIot);

        irrigationLog = irrigationLogRepository.save(irrigationLog);
        return irrigationLog;
    }

    private void handleSlotStatus(IrrigationLog irrigationLog, boolean isIot) {

        Boolean requestStatus = iotSlotService.irrigation(irrigationLog.getSlot());

        if (isIot) {
            log.debug("Updating Slot Status by IoT");
        } else if (requestStatus.equals(true)) {
            irrigationLog.setStatus(SlotStatus.COMPETE_OPEN_REQUEST);
        } else {
            irrigationLog.setStatus(SlotStatus.ERROR_OPEN_REQUEST);
        }
    }

    public void irrigation(List<Slot> slots) {

        List<IrrigationLog> irrigationLogs = slots.stream().map(slot -> {
            IrrigationLog irrigationLog = IrrigationLog.builder()
                    .slot(slot)
                    .build();

            handleSlotStatus(irrigationLog, false);

            return irrigationLog;

        }).toList();

        irrigationLogRepository.saveAll(irrigationLogs);






    }

}
