package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.exception.NotFound;
import com.company.automaticirrigationsystem.model.IrrigationLog;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import com.company.automaticirrigationsystem.model.enums.SlotStatus;
import com.company.automaticirrigationsystem.repository.IrrigationLogRepository;
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
import static org.mockito.Mockito.spy;

@ExtendWith(SpringExtension.class)
class IrrigationLogServiceTest {

    @Mock
    private IrrigationLogRepository irrigationLogRepository;
    @Mock
    private SlotService slotService;
    @Mock
    private IotSlotService iotSlotService;

    @InjectMocks
    private IrrigationLogService irrigationLogService;
    private IrrigationLogService irrigationLogServiceSpy;
    private IrrigationLog irrigationLog = IrrigationLog.builder().id(1L).build();
    private Slot slot = new Slot(1L);


    @BeforeEach
    void setUp() {
        irrigationLogServiceSpy = spy(irrigationLogService);
    }

    @Test
    void findAll() {
        given(irrigationLogRepository.findAll()).willReturn(List.of(irrigationLog));
        irrigationLogService.findAll();
        then(irrigationLogRepository).should().findAll();
    }

    @Nested
    class findById {

        @Test
        void notFoundAndThrow() {
            irrigationLog.setId(1L);
            given(irrigationLogRepository.findById(any())).willThrow(NotFound.class);
            Assertions.assertThrows(NotFound.class, () -> irrigationLogService.findById(1L));
            then(irrigationLogRepository).should().findById(any());
        }

        @Test
        void found() {
            irrigationLog.setId(1L);
            given(irrigationLogRepository.findById(any())).willReturn(Optional.of(irrigationLog));
            irrigationLogService.findById(1L);
            then(irrigationLogRepository).should().findById(1L);
        }
    }

    @Test
    void saveIrrigationLog() {
        irrigationLog.setId(1L);
        given(irrigationLogRepository.save(any())).willReturn(irrigationLog);

        Assertions.assertEquals(
                1L,
                irrigationLogService.saveIrrigationLog(
                        IrrigationLog.builder().id(2L).build()
                ).getId()
        );

        then(irrigationLogRepository).should().save(any());
    }

    @Test
    void saveIrrigationLogAndFindSlot() {
        given(slotService.findById(any())).willReturn(slot);

        willReturn(irrigationLog)
                .given(irrigationLogServiceSpy).saveIrrigationLog(any());

        Assertions.assertNotNull(
                irrigationLogServiceSpy.saveIrrigationLogAndFindSlot(
                        IrrigationLog.builder().slot(slot).build()
                )
        );

        then(slotService).should().findById(any());
        then(irrigationLogServiceSpy).should().saveIrrigationLog(any());
    }

    @Test
    void irrigationAllAndLog() {
        given(iotSlotService.irrigationWithRetry(any(), any())).willReturn(true);
        given(irrigationLogRepository.saveAll(any())).willReturn(List.of());
        willReturn(SlotStatus.COMPETE_IRRIGATION)
                .given(irrigationLogServiceSpy).mappingIotRequestStatusToSlotStatus(true);

        slot.setPlot(Plot.builder().id(1L).build());
        irrigationLogServiceSpy.irrigationAllAndLog(List.of(slot));

        then(irrigationLogRepository).should().saveAll(any());
        then(iotSlotService).should().irrigationWithRetry(any(), any());

    }

    @Test
    void mappingIrrigationRequestStatusToSlotStatus() {
        Assertions.assertEquals(SlotStatus.COMPETE_OPEN_REQUEST,
                irrigationLogService.mappingIotRequestStatusToSlotStatus(true));

        Assertions.assertEquals(SlotStatus.ERROR_OPEN_REQUEST,
                irrigationLogService.mappingIotRequestStatusToSlotStatus(false));
    }
}