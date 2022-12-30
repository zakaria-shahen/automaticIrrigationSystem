package com.company.automaticirrigationsystem.model;

import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SQLDelete(sql = "update irrigation_log set deleted=true where id= ?")
@Where(clause = "deleted=false")
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

    @Column(columnDefinition = "tinyint", nullable = false)
    private SlotStatus status;

    private String details;

    @ManyToOne(optional = false)
    private Slot slot;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

}
