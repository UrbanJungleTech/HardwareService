package urbanjungletech.hardwareservice.addition.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.hardware.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.event.hardware.HardwareEventPublisher;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.helpers.mock.hardware.MockHardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HardwareAdditionServiceImplTest {

    @Mock
    private HardwareDAO hardwareDAO;
    @Mock
    private TimerAdditionService timerAdditionService;
    @Mock
    private HardwareConverter hardwareConverter;
    @Mock
    private ObjectLoggerService objectLoggerService;
    @Mock
    private HardwareStateConverter hardwareStateConverter;
    @Mock
    private HardwareEventPublisher hardwareEventPublisher;
    @Mock
    private HardwareStateAdditionService hardwareStateAdditionService;
    @Mock
    private HardwareQueryService hardwareQueryService;

    @InjectMocks
    private HardwareAdditionServiceImpl hardwareAdditionService;

    @Test
    void testCreateHardware() {
        Hardware hardware = new MockHardware();
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setId(1L);
        HardwareState offHardwareState = new HardwareState();

        when(hardwareDAO.createHardware(any())).thenReturn(hardwareEntity);
        when(hardwareConverter.toModel(any())).thenReturn(hardware);

        Hardware createdHardware = hardwareAdditionService.create(hardware);

        verify(hardwareDAO).createHardware(hardware);
        verify(hardwareStateAdditionService, times(2)).create(any(HardwareState.class), any());
        assertNotNull(createdHardware);
    }


    @Test
    void testUpdateHardwareFailure() {
        long hardwareId = 1L;
        Hardware hardware = mock(Hardware.class);
        when(hardwareDAO.updateHardware(eq(hardware))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> hardwareAdditionService.update(hardwareId, hardware));
    }

    @Test
    void testDeleteHardwareSuccess() {
        long hardwareId = 1L;

        when(this.hardwareQueryService.getHardware(hardwareId)).thenReturn(new MockHardware());
        this.hardwareAdditionService.delete(hardwareId);

        verify(hardwareDAO).delete(hardwareId);
        verify(hardwareEventPublisher).publishDeleteHardwareEvent(hardwareId);
    }

    @Test
    void testDeleteHardwareNotFoundException() {
        long hardwareId = 1L;
        when(this.hardwareQueryService.getHardware(hardwareId)).thenThrow(new NotFoundException("Hardware", hardwareId));
        assertThrows(NotFoundException.class, () -> this.hardwareAdditionService.delete(hardwareId));
    }

}
