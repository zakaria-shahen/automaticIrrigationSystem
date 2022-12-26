package com.company.automaticirrigationsystem.repository;

import com.company.automaticirrigationsystem.model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
}
