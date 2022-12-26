package com.company.automaticirrigationsystem.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HomeDto extends RepresentationModel<HomeDto> {
    private String test;

}
