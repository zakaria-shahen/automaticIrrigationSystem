package com.company.automaticirrigationsystem.repository;

import com.company.automaticirrigationsystem.model.Plot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends CrudRepository<Plot, Long> {
}
