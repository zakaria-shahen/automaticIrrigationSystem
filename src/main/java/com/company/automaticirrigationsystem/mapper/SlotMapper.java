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
    SlotDto slotToSlotDto(Slot slotDto);

    List<SlotDto> slotToSlotDto(List<Slot> slotDto);

    @Mapping(source = "plotId", target = "plot.id")
    Slot slotDtoToSlot(SlotDto slot);

    List<Slot> slotDtoToSlot(List<SlotDto> slotDto);
}
