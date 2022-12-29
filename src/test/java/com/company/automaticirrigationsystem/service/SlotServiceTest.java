package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.repository.SlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;
    @Mock
    private PlotService plotService;
    @InjectMocks
    private SlotService slotService;
    private final Slot slot = Slot.builder().id(1L).build();
    private final List<Slot> slots = List.of(slot, slot);
    private final Plot plot = Plot.builder().id(1L).build();

    @Test
    void findAll() {
        given(slotRepository.findAll()).willReturn(slots);
        Assertions.assertEquals(slots, slotService.findAll());
        then(slotRepository).should().findAll();
    }

    @Nested
    class findByIdTest {
        @Test
        void found() {
            given(slotRepository.findById(1L)).willReturn(Optional.of(slot));
            slotService.findById(1L);
            then(slotRepository).should().findById(1L);
        }

        @Test
        void notFoundAndThrow() {
            given(slotRepository.findById(any())).willReturn(Optional.empty());
            Assertions.assertThrows(NotFound.class, () -> slotService.findById(1L));
            then(slotRepository).should().findById(any());
        }
    }

    @Test
    void create() {
        given(slotRepository.save(any())).willReturn(Slot.builder().id(1L).build());
        given(plotService.findById(any())).willReturn(plot);
        slot.setId(2L);
        slot.setPlot(plot);
        Assertions.assertEquals(1L, slotService.create(slot).getId());
        then(slotRepository).should().save(slot);
        then(plotService).should().findById(1L);
    }

    @Test
    void deleteById() {
        willDoNothing().given(slotRepository).deleteById(any());
        slotService.deleteById(any());
        then(slotRepository).should().deleteById(any());
    }
}