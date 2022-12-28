package com.company.automaticirrigationsystem.mapper;

import com.company.automaticirrigationsystem.dto.IrrigationLogDto;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IrrigationLogMapper {

    IrrigationLogMapper INSTANCE = Mappers.getMapper(IrrigationLogMapper.class);

    @Mapping(source = "slot.id", target = "slotId")
    IrrigationLogDto irrigationLogToDto(IrrigationLog irrigationLog);

    List<IrrigationLogDto> irrigationLogToDto(List<IrrigationLog> irrigationLog);

    @Mapping(source = "slotId", target =  "slot.id")
    @Mapping(target = "slot", ignore = true)
    @Mapping(target = "dateTime", ignore = true)
    IrrigationLog dtoToIrrigationLog(IrrigationLogDto irrigationLogDto);

    List<IrrigationLog> dtoToIrrigationLog(List<IrrigationLogDto> irrigationLogDto);

}
