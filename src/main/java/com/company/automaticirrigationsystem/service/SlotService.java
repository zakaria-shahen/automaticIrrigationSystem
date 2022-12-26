package com.company.automaticirrigationsystem.service;


import com.company.automaticirrigationsystem.exception.IdNotEntered;
import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotService {

    private final SlotRepository slotRepository;
    private final PlotService plotService;


    public List<Slot> findAll() {
        return slotRepository.findAll();
    }

    public Slot findById(Long id) {
        log.debug("Retrieving the Slot entity with ID={} from the datastore.", id);
        return slotRepository.findById(id)
                .orElseThrow(() ->  new NotFound(Slot.class));
    }

    public Slot create(Slot slot) {
        slot.setId(null);
        slot.setPlot(plotService.findById(slot.getPlot().getId()));

        log.debug("storing new Slot entity to datastore");

        return slotRepository.save(slot);
    }


    public void deleteById(Long id) {
        log.debug("deleting Slot entity with id={} from datastore", id);
        slotRepository.deleteById(id);
    }

    public Slot partialUpdate(Slot newer) {
        if (Objects.isNull(newer.getId())) {
            throw new IdNotEntered();
        }

        Slot target = findById(newer.getId());

        newer.setPlot(plotService.getLatestPlot(newer, target));
        BeanUtils.copyProperties(newer, target, "id", "irrigationLogs");

        log.debug("updating Slot entity with id={} from datastore", target.getId());

        return slotRepository.save(target);
    }


}
