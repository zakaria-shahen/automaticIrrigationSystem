package com.company.automaticirrigationsystem.service;


import com.company.automaticirrigationsystem.exception.IdNotEntered;
import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.repository.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;

    public List<Plot> findAll() {
        return plotRepository.findAll();
    }

    public Plot create(Plot plot) {
        plot.setId(null);

        return plotRepository.save(plot);
    }

    public Plot findById(Long id) {
        return plotRepository.findById(id)
                .orElseThrow(() ->  new NotFound(Plot.class));
    }

    public void deleteById(Long id) {
        plotRepository.deleteById(id);
    }

    public Plot partialUpdate(Plot plot) {
        if (Objects.isNull(plot.getId())) {
            throw new IdNotEntered();
        }

        Plot target = findById(plot.getId());

        BeanUtils.copyProperties(plot, target, "id", "slots");

        return plotRepository.save(target);
    }
}
