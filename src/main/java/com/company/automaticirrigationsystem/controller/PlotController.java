package com.company.automaticirrigationsystem.controller;

import com.company.automaticirrigationsystem.dto.PlotDto;
import com.company.automaticirrigationsystem.mapper.PlotMapper;
import com.company.automaticirrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plot")
@RequiredArgsConstructor
public class PlotController {

    private final PlotService plotService;
    private final PlotMapper plotMapper;

    @GetMapping
    public List<PlotDto> getAll() {
        return plotMapper.plotToDto(
                plotService.findAll()
        );
    }

    @GetMapping("/{id}")
    public PlotDto getOne(@PathVariable Long id) {
        return plotMapper.plotToDto(
                plotService.findById(id)
        );
    }

    @PostMapping
    public PlotDto post(@RequestBody PlotDto plotDto) {
        var plot =  plotService.create(plotMapper.dtoToPlot(plotDto));
        return plotMapper.plotToDto(plot);
    }

    @PatchMapping
    public PlotDto patch(@RequestBody PlotDto plotDto) {
        var plot =  plotService.partialUpdate(plotMapper.dtoToPlot(plotDto));
        return plotMapper.plotToDto(plot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        plotService.deleteById(id);
    }

}
