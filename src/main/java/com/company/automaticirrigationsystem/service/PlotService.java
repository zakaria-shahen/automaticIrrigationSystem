package com.company.automaticirrigationsystem.service;


import com.company.automaticirrigationsystem.exception.IdNotEntered;
import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlotService {

    private final PlotRepository plotRepository;

    public List<Plot> findAll() {
        return plotRepository.findAll();
    }

    public Plot create(Plot plot) {
        plot.setId(null);
        log.debug("storing new Plot entity to datastore");

        return plotRepository.save(plot);
    }

    public Plot findById(Long id) {
        log.debug("Retrieving the plot entity with ID={} from the datastore.", id);
        return plotRepository.findById(id)
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
}