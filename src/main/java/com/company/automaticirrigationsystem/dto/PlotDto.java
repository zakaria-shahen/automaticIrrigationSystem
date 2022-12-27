package com.company.automaticirrigationsystem.dto;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero
    private Integer waitBeforeCloseSlots;

    @PositiveOrZero
    private Float amountWater;

    @Positive
    private Integer irrigationEvery;

    @PositiveOrZero
    private Integer retryCallLimit;

    private List<SlotDto> slots;

}
