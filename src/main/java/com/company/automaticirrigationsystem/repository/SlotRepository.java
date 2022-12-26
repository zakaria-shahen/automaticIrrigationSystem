package com.company.automaticirrigationsystem.repository;

import com.company.automaticirrigationsystem.model.Slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends CrudRepository<Slot, Long> {
}
