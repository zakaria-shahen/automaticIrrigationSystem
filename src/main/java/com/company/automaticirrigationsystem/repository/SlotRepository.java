package com.company.automaticirrigationsystem.repository;

import com.company.automaticirrigationsystem.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
}
