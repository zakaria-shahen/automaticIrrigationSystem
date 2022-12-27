package com.company.automaticirrigationsystem.event.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@Deprecated
public class FailureIrrigationLogModel {
    private Long id;
    private Integer waitBeforeAlert;
    private Long slotId;
}
