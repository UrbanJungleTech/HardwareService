package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.exception.StandardNotFoundException;
import frentz.daniel.hardwareservice.helper.HardwareHelper;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HardwareDAOImplTest {

    @Mock
    private HardwareRepository hardwareRepository;
    @Mock
    private HardwareConverter hardwareConverter;
    @Mock
    private ExceptionService exceptionService;
    @Mock
    private HardwareControllerRepository hardwareControllerRepository;

    @InjectMocks
    private HardwareDAOImpl hardwareDAO;

    @Test
    void testGetHardware() {
        long hardwareId = 1;
        HardwareEntity hardwareEntity = new HardwareEntity();
        Optional<HardwareEntity> hardwareEntityOptional = Optional.of(hardwareEntity);
        when(hardwareRepository.findById(hardwareId)).thenReturn(hardwareEntityOptional);
        HardwareEntity result = this.hardwareDAO.getHardware(hardwareId);
        assertEquals(result, hardwareEntity);
    }

    @Test()
    void testGetHardwareNotFound() {
        long hardwareId = 1;
        Optional<HardwareEntity> hardwareEntityOptional = Optional.empty();
        when(hardwareRepository.findById(hardwareId)).thenReturn(hardwareEntityOptional);
        when(exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new StandardNotFoundException("hardware", "1"));
        assertThrows(StandardNotFoundException.class, () -> this.hardwareDAO.getHardware(hardwareId));

    }

    @Test
    public void testCreateHardware(){
        Hardware hardware = HardwareHelper.createHardware();
        hardware.setHardwareControllerId(1L);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setId(1L);
        HardwareEntity hardwareEntity = new HardwareEntity();
        when(this.hardwareControllerRepository.findById(anyLong())).thenReturn(Optional.of(hardwareControllerEntity));
        when(this.hardwareRepository.save(any())).thenReturn(hardwareEntity);
        HardwareEntity result = this.hardwareDAO.createHardware(hardware);
        assertTrue(hardwareControllerEntity.getHardware().contains(result));
    }

    @Test
    public void testCreateHardwareControllerNotFound(){
        Hardware hardware = new Hardware();
        hardware.setHardwareControllerId(1L);
        when(this.hardwareControllerRepository.findById(any())).thenReturn(Optional.empty());
        when(exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new StandardNotFoundException("hardware controller", "1"));
        assertThrows(StandardNotFoundException.class, () -> this.hardwareDAO.createHardware(hardware));
    }

    @Test
    public void updateHardware_shouldReturnHardwareEntity_whenHardwareExists(){
        Hardware hardware = new Hardware();
        HardwareEntity hardwareEntity = new HardwareEntity();

        when(this.hardwareRepository.findById(any())).thenReturn(Optional.of(hardwareEntity));

        HardwareEntity result = this.hardwareDAO.updateHardware(hardware);

        assertSame(result, hardwareEntity);
        verify(this.hardwareRepository, times(1)).save(hardwareEntity);
    }

    @Test
    public void updateHardware_shouldThrowNotFoundException_whenHardwareNotFound(){
        Hardware hardware = new Hardware();
        hardware.setId(1L);

        when(this.hardwareRepository.findById(any())).thenReturn(Optional.empty());
        when(this.exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new StandardNotFoundException("hardware", "1"));

        assertThrows(StandardNotFoundException.class, () -> this.hardwareDAO.updateHardware(hardware));
    }
}