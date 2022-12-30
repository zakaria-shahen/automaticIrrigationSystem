package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.event.SendIrrigationRequestEvent;
import com.company.automaticirrigationsystem.exception.IdNotEntered;
import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.repository.PlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class PlotServiceTest {

    @Mock
    private PlotRepository plotRepository;
    @Mock
    private SendIrrigationRequestEvent sendIrrigationRequestEvent;
    @InjectMocks
    private PlotService plotService;
    private final Plot plot = Plot.builder().id(1L).build();
    private final List<Plot> plots = List.of(plot);
    private final Slot slot = Slot.builder().id(1L).build();

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        given(plotRepository.findAll()).willReturn(plots);
        Assertions.assertEquals(plots, plotService.findAll());
        then(plotRepository).should().findAll();

    }

    @Test
    void create() {
        Plot returnPlot = Plot.builder().id(1L).build();
        given(plotRepository.save(any())).willReturn(returnPlot);
        willDoNothing().given(sendIrrigationRequestEvent).publisher(any());
        plot.setId(2L);
        Assertions.assertEquals(1L, plotService.create(plot).getId());
        then(plotRepository).should().save(plot);
        then(sendIrrigationRequestEvent).should().publisher(returnPlot);
    }

    @Nested
    class findById {
        @Test
        void found() {
            given(plotRepository.findById(any())).willReturn(Optional.of(plot));
            plotService.findById(1L);
            then(plotRepository).should().findById(1L);
        }

        @Test
        void notFoundAndThrow() {
            given(plotRepository.findById(any())).willReturn(Optional.empty());
            Assertions.assertThrows(NotFound.class, () -> plotService.findById(1L));
            then(plotRepository).should().findById(1L);
        }
    }

    @Nested
    class findByIdAndLoadSlots {
        @Test
        void found() {
            given(plotRepository.findByIdAndLoadSlots(any())).willReturn(Optional.of(plot));
            plotService.findByIdAndLoadSlots(1L);
            then(plotRepository).should().findByIdAndLoadSlots(1L);
        }

        @Test
        void notFoundAndReturnEmptyOptional() {
            given(plotRepository.findByIdAndLoadSlots(any())).willReturn(Optional.empty());
            Assertions.assertTrue(plotService.findByIdAndLoadSlots(1L).isEmpty());
            then(plotRepository).should().findByIdAndLoadSlots(1L);
        }
    }

    @Test
    void deleteById() {
        willDoNothing().given(plotRepository).deleteById(any());
        plotService.deleteById(1L);
        then(plotRepository).should().deleteById(1L);
    }


    @Nested
    class partialUpdate {

        private final PlotService plotServiceSpy = spy(plotService);

        @Test
        void throwIfPlotIdNull() {
            plot.setId(null);
            Assertions.assertThrows(IdNotEntered.class, () -> plotService.partialUpdate(plot));
        }

        @Test
        void updateTest() {
            plot.setSlots(List.of(slot));
            given(plotRepository.save(any())).willReturn(plot);
            willReturn(plot).given(plotServiceSpy).findById(any());

            Plot input = Plot.builder().id(1L).slots(null).build();
            Plot output = plotServiceSpy.partialUpdate(input);
            Assertions.assertNotNull(output.getSlots());
            Assertions.assertEquals(1, output.getSlots().size());
            Assertions.assertEquals(slot, output.getSlots().get(0));

            then(plotRepository).should().save(any());

        }
    }
}