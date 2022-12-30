package com.company.automaticirrigationsystem.model;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@SQLDelete(sql = "update slot set deleted=true where id= ?")
@Where(clause = "deleted=false")
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

    @OneToMany(mappedBy = "slot", cascade = CascadeType.REMOVE)
    private List<IrrigationLog> irrigationLogs;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    public Slot(Long id) {
        this.id = id;
    }
}
