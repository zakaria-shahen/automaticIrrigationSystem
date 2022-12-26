package com.company.automaticirrigationsystem.mapper;

import com.company.automaticirrigationsystem.dto.PlotDto;
import com.company.automaticirrigationsystem.model.Plot;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlotMapper {

    PlotMapper INSTANCES = Mappers.getMapper(PlotMapper.class);

    PlotDto plotToDto(Plot plot);

    List<PlotDto> plotToDto(List<Plot> plots);

    // @Mapping(target = "slots", ignore = true)
    Plot dtoToPlot(PlotDto plotDto);

}
