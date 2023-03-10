package com.company.automaticirrigationsystem.model;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@SQLDelete(sql = "update plot set deleted=true where id= ?")
@Where(clause = "deleted=false")
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
    @Column(nullable = false)
    private Integer waitBeforeCloseSlots;

    /**
     *  Amount of water per irrigation period
     */
    private Float amountWater;

    /**
     *  Irrigation every X ms
     */
    @Builder.Default
    @Column(nullable = false)
    private Integer irrigationEvery = (int) TimeUnit.HOURS.toMillis(8);

    /**
     * Retry calls (If the irrigation request fails to be sent) before send Alert
     */
    @Builder.Default
    @Column(nullable = false)
    private Integer retryBeforeAlert = 3;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.REMOVE)
    private List<Slot> slots;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}
