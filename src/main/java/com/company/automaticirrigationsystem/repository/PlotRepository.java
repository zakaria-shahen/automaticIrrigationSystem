package com.company.automaticirrigationsystem.repository;

import com.company.automaticirrigationsystem.model.Plot;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {

    @EntityGraph(attributePaths = "slots")
    @Query("select p from Plot p where p.id = ?1")
    Optional<Plot> findByIdAndLoadSlots(@NotNull Long id);

}
