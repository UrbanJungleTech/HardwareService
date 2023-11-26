package urbanjungletech.hardwareservice.addition.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.HardwareAdditionServiceImpl;
import urbanjungletech.hardwareservice.builder.HardwareStateBuilder;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.event.hardware.HardwareEventPublisher;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.ObjectLoggerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void testUpdateHardwareSuccess() {
        // Arrange
        long hardwareId = 1L;
        Hardware hardware = new Hardware();
        HardwareEntity hardwareEntity = new HardwareEntity();
        hardwareEntity.setId(hardwareId);
        HardwareState desiredState = new HardwareState();
        desiredState.setId(1L);
        hardware.setDesiredState(desiredState);
        HardwareState currentState = new HardwareState();
        currentState.setId(2L);
        hardware.setCurrentState(currentState);
        when(hardwareDAO.updateHardware(eq(hardware))).thenReturn(hardwareEntity);
        when(hardwareConverter.toModel(eq(hardwareEntity))).thenReturn(hardware);
        when(hardware.getDesiredState()).thenReturn(desiredState);
        when(hardware.getCurrentState()).thenReturn(currentState);
        when(hardware.getId()).thenReturn(hardwareId);

        // Act
        Hardware updatedHardware = hardwareAdditionService.update(hardwareId, hardware);

        // Assert
        verify(hardwareDAO).updateHardware(hardware);
        verify(hardwareStateAdditionService).update(anyLong(), eq(desiredState));
        verify(hardwareStateAdditionService).update(anyLong(), eq(currentState));
        assertEquals(hardware, updatedHardware);
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
        // Arrange
        long hardwareId = 1L;
        doThrow(new RuntimeException("Database error")).when(hardwareDAO).delete(hardwareId);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> hardwareAdditionService.delete(hardwareId));
        assertEquals("Database error", exception.getMessage());
    }

}
