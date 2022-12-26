package com.company.automaticirrigationsystem.dto;

import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IrrigationLogDto extends RepresentationModel<IrrigationLogDto> {

    private Long id;

    private LocalDateTime dateTime;

    private SlotStatus status;

    private String details;

    private Long slotId;

}
