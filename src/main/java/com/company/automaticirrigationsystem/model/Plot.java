package com.company.automaticirrigationsystem.model;


import lombok.*;

import javax.persistence.*;
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

    /**
     * Wait X ms before close slot
     */
    private Integer waitBeforeCloseSlots;

    /**
     *  Amount of water per irrigation period
     */
    private Float amountWater;

    /**
     *  Irrigation every X ms
     */
    private Integer irrigationEvery;

    /**
     * Retry calls (If the irrigation request fails to be sent) before send Alert
     */
    private Integer retryCallLimit;

    @OneToMany(mappedBy = "plot")
    private List<Slot> slots;
}
