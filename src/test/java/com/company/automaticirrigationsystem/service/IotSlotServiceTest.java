package com.company.automaticirrigationsystem.service;

import com.company.automaticirrigationsystem.event.FailureIrrigationLogEvent;
import com.company.automaticirrigationsystem.model.Plot;
import com.company.automaticirrigationsystem.model.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.spy;

@ExtendWith(SpringExtension.class)
class IotSlotServiceTest {

    @Mock
    private FailureIrrigationLogEvent failureIrrigationLogEvent;
    @Mock
    private AlertService alertService;
    @InjectMocks
    private IotSlotService iotSlotService;
    private IotSlotService iotSlotServiceSpy;
    private final Slot slot = new Slot(1L);

    @BeforeEach
    void setUp() {
        iotSlotServiceSpy = spy(iotSlotService);
    }

    @Test
    void irrigation() {
        willDoNothing().given(failureIrrigationLogEvent).publishing(anyLong(), anyInt());
        slot.setPlot(Plot.builder().waitBeforeCloseSlots(10).build());
        Assertions.assertTrue(iotSlotService.irrigation(slot));
        then(failureIrrigationLogEvent).should().publishing(anyLong(), anyInt());
    }


    @Nested
    class irrigationWithRetry {
        @Test
        void oneAttempts() {
            willReturn(true).given(iotSlotServiceSpy).irrigation(any());
            Assertions.assertTrue(iotSlotServiceSpy.irrigationWithRetry(slot, 1));
            then(iotSlotServiceSpy).should().irrigation(any());
        }

        @Test
        void twoAttempts() {
            willReturn(false).given(iotSlotServiceSpy).irrigation(any());
            willDoNothing().given(alertService).alert(any());

            Assertions.assertFalse(iotSlotServiceSpy.irrigationWithRetry(slot, 1));

            then(iotSlotServiceSpy).should(times(2)).irrigation(any());
            then(alertService).should().alert(any());
        }
    }

}