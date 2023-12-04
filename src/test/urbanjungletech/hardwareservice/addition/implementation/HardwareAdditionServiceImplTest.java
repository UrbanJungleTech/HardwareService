package urbanjungletech.hardwareservice.addition.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.builder.HardwareStateBuilder;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.event.hardware.HardwareEventPublisher;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;

import static org.junit.jupiter.api.Assertions.*;
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
    private HardwareStateBuilder hardwareStateBuilder;
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

    @InjectMocks
    private HardwareAdditionServiceImpl hardwareAdditionService;

    @Test
    void testCreateHardware() {
        // Arrange
        Hardware hardware = new Hardware();
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setId(1L);
        HardwareState offHardwareState = new HardwareState();

        when(hardwareDAO.createHardware(any())).thenReturn(hardwareEntity);
        when(hardwareConverter.toModel(any())).thenReturn(hardware);
        when(hardwareStateBuilder.getOffHardwareState()).thenReturn(offHardwareState);

        // Act
        Hardware createdHardware = hardwareAdditionService.create(hardware);

        // Assert
        verify(hardwareDAO).createHardware(hardware);
        verify(hardwareStateAdditionService, times(2)).create(any(HardwareState.class), any());
        assertNotNull(createdHardware);
    }


    @Test
    void testUpdateHardwareFailure() {
        // Arrange
        long hardwareId = 1L;
        Hardware hardware = mock(Hardware.class);
        when(hardwareDAO.updateHardware(eq(hardware))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> hardwareAdditionService.update(hardwareId, hardware));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testDeleteHardwareSuccess() {
        // Arrange
        long hardwareId = 1L;

        // Act
        hardwareAdditionService.delete(hardwareId);

        // Assert
        verify(hardwareDAO).delete(hardwareId);
        verify(hardwareEventPublisher).publishDeleteHardwareEvent(hardwareId);
    }

    @Test
    void testDeleteHardwareFailure() {
        long hardwareId = 1L;
        doThrow(new RuntimeException("Database error")).when(hardwareDAO).delete(hardwareId);

        Exception exception = assertThrows(RuntimeException.class, () -> hardwareAdditionService.delete(hardwareId));
        assertEquals("Database error", exception.getMessage());
    }

}
