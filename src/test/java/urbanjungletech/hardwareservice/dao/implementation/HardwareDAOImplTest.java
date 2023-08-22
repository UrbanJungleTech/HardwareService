package urbanjungletech.hardwareservice.dao.implementation;

import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.exception.NotFoundException;
import urbanjungletech.hardwareservice.helper.HardwareHelper;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import urbanjungletech.hardwareservice.service.exception.ExceptionService;
import urbanjungletech.hardwareservice.model.Hardware;
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
        when(exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new NotFoundException("hardware", "1"));
        assertThrows(NotFoundException.class, () -> this.hardwareDAO.getHardware(hardwareId));

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
        when(exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new NotFoundException("hardware controller", "1"));
        assertThrows(NotFoundException.class, () -> this.hardwareDAO.createHardware(hardware));
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
        when(this.exceptionService.createNotFoundException(any(), anyLong())).thenReturn(new NotFoundException("hardware", "1"));

        assertThrows(NotFoundException.class, () -> this.hardwareDAO.updateHardware(hardware));
    }
}
