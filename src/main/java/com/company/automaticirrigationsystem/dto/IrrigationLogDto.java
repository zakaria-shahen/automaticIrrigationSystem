package com.company.automaticirrigationsystem.dto;

import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IrrigationLogDto extends RepresentationModel<IrrigationLogDto> {

    private Long id;

    @PastOrPresent
    private LocalDateTime dateTime;

    @NotNull
    private SlotStatus status;

    private String details;

    @NotNull
    private Long slotId;

}
