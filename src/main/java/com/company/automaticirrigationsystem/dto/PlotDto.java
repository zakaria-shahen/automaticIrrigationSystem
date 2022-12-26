package com.company.automaticirrigationsystem.dto;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlotDto extends RepresentationModel<PlotDto> {

    private Long id;

    private String agriculturalCrop;

    private Long cultivatedArea;

    private LocalTime waitBeforeCloseSlots;

    private Float amountWater;

    private LocalTime irrigationEvery;

    private Integer retryCallLimit;

    private List<SlotDto> slots;

}
