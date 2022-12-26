package com.company.automaticirrigationsystem.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agriculturalCrop;

    private Long cultivatedArea;

    private LocalTime waitBeforeCloseSlots;

    /**
     *  Amount of water per irrigation period
     */
    private Float amountWater;


    /**
     *  Irrigation every X hour
     */
    private LocalTime irrigationEvery;

    /**
     * Retry calls before send Alert
     */
    private Integer retryCallLimit;

    @OneToMany(mappedBy = "plot")
    private List<Slot> slots;
}
