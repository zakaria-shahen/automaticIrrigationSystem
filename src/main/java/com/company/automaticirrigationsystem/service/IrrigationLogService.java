package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IrrigationLogService {

    private final IrrigationLogRepository irrigationLogRepository;
    private final SlotService slotService;
    private final IotSlotService iotSlotService;
    private final AlertService alertService;

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

    public void irrigationAll(@NotEmpty List<Slot> slots) {

        log.debug("irrigation Plot with id={}", slots.get(0).getPlot().getId());

        List<IrrigationLog> irrigationLogs = slots.stream().map(slot -> {
            IrrigationLog irrigationLog = IrrigationLog.builder()
                    .slot(slot)
                    .build();

            boolean requestStatus = irrigation(irrigationLog);
            irrigationLog.setStatus(mappingRequestStatusToSlotStatus(requestStatus));
            return irrigationLog;

        }).toList();

        irrigationLogRepository.saveAll(irrigationLogs);
    }

    private Boolean irrigation(@NotNull IrrigationLog irrigationLog) {
        Slot slot = irrigationLog.getSlot();
        Boolean requestStatus = iotSlotService.irrigation(slot);
        int retryBeforeAlert = irrigationLog.getSlot().getPlot().getRetryCallLimit();

        while (requestStatus.equals(false) && retryBeforeAlert > 0) {
            retryBeforeAlert--;
            requestStatus = iotSlotService.irrigation(slot);
        }

        if (requestStatus.equals(false)) {
            String message = "IoT not available";
            log.debug(message);
            alertService.alert(message);
        }
        
        return requestStatus;
    }

    private SlotStatus mappingRequestStatusToSlotStatus(@NotNull boolean requestStatus) {
        return requestStatus
                ? SlotStatus.COMPETE_OPEN_REQUEST
                : SlotStatus.ERROR_OPEN_REQUEST;
    }


}
