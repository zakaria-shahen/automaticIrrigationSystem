package com.company.automaticirrigationsystem.mapper;

import com.company.automaticirrigationsystem.dto.SlotDto;
import com.company.automaticirrigationsystem.model.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SlotMapper {

    SlotMapper INSTANCE = Mappers.getMapper(SlotMapper.class);

    @Mapping(source = "plot.id", target = "plotId")
    SlotDto slotToDto(Slot slotDto);

    List<SlotDto> slotToDto(List<Slot> slotDto);

    @Mapping(source = "plotId", target = "plot.id")
    Slot dtoToSlot(SlotDto slot);

    List<Slot> dtoToSlot(List<SlotDto> slotDto);
}
