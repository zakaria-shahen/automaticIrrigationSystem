package com.company.automaticirrigationsystem.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SlotDto extends RepresentationModel<SlotDto> {
    private Long id;

    private Integer retryCall;

    private Long plotId;

    private List<IrrigationLogDto> irrigationLogs;
}
