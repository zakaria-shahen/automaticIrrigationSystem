package com.company.automaticirrigationsystem.service;


import com.company.automaticirrigationsystem.event.SendIrrigationRequestEvent;
import com.company.automaticirrigationsystem.exception.IdNotEntered;
import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.repository.PlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PlotService {

    private final PlotRepository plotRepository;
    private final SendIrrigationRequestEvent sendIrrigationRequestEvent;

    public PlotService(PlotRepository plotRepository, @Lazy SendIrrigationRequestEvent sendIrrigationRequestEvent) {
        this.plotRepository = plotRepository;
        this.sendIrrigationRequestEvent = sendIrrigationRequestEvent;
    }

    public List<Plot> findAll() {
        return plotRepository.findAll();
    }

    public Plot create(Plot plot) {
        plot.setId(null);
        log.debug("storing new Plot entity to datastore");

        plot = plotRepository.save(plot);

        sendIrrigationRequestEvent.publisher(plot);

        return plot;

    }

    public Plot findById(Long id) {
        log.debug("Retrieving the plot entity with ID={} from the datastore.", id);
        return plotRepository.findById(id)
                .orElseThrow(() ->  new NotFound(Plot.class));
    }

    public Plot findByIdAndLoadSlots(Long id) {
        log.debug("Retrieving the plot entity with ID={} from the datastore.", id);
        return plotRepository.findByIdAndLoadSlots(id)
                .orElseThrow(() ->  new NotFound(Plot.class));
    }

    public void deleteById(Long id) {
        log.debug("deleting plot entity with id={} from datastore", id);
        plotRepository.deleteById(id);
    }

    public Plot partialUpdate(Plot plot) {
        if (Objects.isNull(plot.getId())) {
            throw new IdNotEntered();
        }

        Plot target = findById(plot.getId());

        BeanUtils.copyProperties(plot, target, "id", "slots");

        log.debug("updating plot entity with id={} from datastore", target.getId());
        return plotRepository.save(target);
    }

    public Plot getLatestPlot(Slot newer, Slot older) {
        if (! older.getPlot().getId().equals(newer.getPlot().getId())) {
            log.debug("Retrieving newer plot from datastore");
            return findById(newer.getPlot().getId());
        }

        log.debug("No difference between newer and older Plot");
        return older.getPlot();
    }
}
