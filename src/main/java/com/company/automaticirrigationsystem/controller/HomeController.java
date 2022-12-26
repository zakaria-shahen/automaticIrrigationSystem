package com.company.automaticirrigationsystem.controller;

import com.company.automaticirrigationsystem.dto.HomeDto;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Object home() {

        return new HomeDto().add(
                Link.of("/").withSelfRel()
        );
    }

}
