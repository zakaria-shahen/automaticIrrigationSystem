package com.company.automaticirrigationsystem.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Plot plot;

    @OneToMany(mappedBy = "slot")
    private List<IrrigationLog> irrigationLogs;

    public Slot(Long id) {
        this.id = id;
    }
}
