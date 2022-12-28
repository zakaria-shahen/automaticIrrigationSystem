package com.company.automaticirrigationsystem.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SlotDto extends RepresentationModel<SlotDto> {
    private Long id;

    @NotNull
    private Long plotId;

    private List<IrrigationLogDto> irrigationLogs;
}
