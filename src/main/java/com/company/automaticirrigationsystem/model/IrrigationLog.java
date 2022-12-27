package com.company.automaticirrigationsystem.model;

import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class IrrigationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();

    private SlotStatus status;

    private String details;

    @ManyToOne(optional = false)
    private Slot slot;

}
