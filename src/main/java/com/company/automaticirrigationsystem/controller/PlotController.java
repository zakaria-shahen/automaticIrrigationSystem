package com.company.automaticirrigationsystem.controller;

import com.company.automaticirrigationsystem.dto.PlotDto;
import com.company.automaticirrigationsystem.mapper.PlotMapper;
import com.company.automaticirrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plot")
@RequiredArgsConstructor
@Slf4j
public class PlotController {

    private final PlotService plotService;
    private final PlotMapper plotMapper;

    @GetMapping
    public List<PlotDto> getAll() {
        log.debug("visited GET `{}` path", "/plot");
        return plotMapper.plotToDto(
                plotService.findAll()
        );
    }

    @GetMapping("/{id}")
    public PlotDto getOne(@PathVariable Long id) {
        log.debug("visited GET `{}` path", "/plot/{id}");
        return plotMapper.plotToDto(
                plotService.findById(id)
        );
    }

    @PostMapping
    public PlotDto post(@RequestBody PlotDto plotDto) {
        log.debug("visited POST `{}` path", "/plot");
        var plot =  plotService.create(plotMapper.dtoToPlot(plotDto));
        return plotMapper.plotToDto(plot);
    }

    @PatchMapping
    public PlotDto patch(@RequestBody PlotDto plotDto) {
        log.debug("visited PATCH `{}` path", "/plot");
        var plot =  plotService.partialUpdate(plotMapper.dtoToPlot(plotDto));
        return plotMapper.plotToDto(plot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("visited DELETE `{}` path", "/plot/{id}");
        plotService.deleteById(id);
    }

}
