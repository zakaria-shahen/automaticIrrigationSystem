package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Slot;
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

    public List<IrrigationLog> findAll() {
        return irrigationLogRepository.findAll();
    }

    public IrrigationLog findById(Long id) {
        log.debug("Retrieving the IrrigationLog entity with ID={} from the datastore.", id);
        return irrigationLogRepository.findById(id)
                .orElseThrow(() ->  new NotFound(IrrigationLog.class));
    }

    public IrrigationLog create(IrrigationLog irrigationLog) {
        irrigationLog.setId(null);
        irrigationLog.setSlot(slotService.findById(irrigationLog.getSlot().getId()));

        log.debug("storing new IrrigationLog entity to datastore");

        return irrigationLogRepository.save(irrigationLog);
    }
}
