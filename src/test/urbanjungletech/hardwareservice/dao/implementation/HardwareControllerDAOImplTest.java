package urbanjungletech.hardwareservice.dao.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HardwareControllerDAOImplTest {

    @Mock
    private HardwareControllerRepository hardwareControllerRepository;
    @Mock
    private HardwareControllerConverter hardwareControllerConverter;
    @Mock
    private ExceptionService exceptionService;
    @InjectMocks
    private HardwareControllerDAOImpl hardwareControllerDAO;

    /**
     * Given a valid HardwareController object
     * When the createHardwareController.createHardwareController method is called
     * Then the hardwareControllerRepository.save method is called with the HardwareController object
     * And the result of the hardwareControllerRepository.save method is returned
     */
    @Test
    void createHardwareController() {
        HardwareController hardwareController = new HardwareController();
        HardwareControllerEntity resultHardwareControllerEntity = new HardwareControllerEntity();


        when(this.hardwareControllerRepository.save(any(HardwareControllerEntity.class))).thenReturn(resultHardwareControllerEntity);

        HardwareControllerEntity result = this.hardwareControllerDAO.createHardwareController(hardwareController);

        assertSame(resultHardwareControllerEntity, result);
    }

    /**
     * When the getAllHardwareControllers method is called
     * Then the hardwareControllerRepository.findAll method is called
     * And the result of the hardwareControllerRepository.findAll method is returned
     */
    @Test
    void getAllHardwareControllers() {
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();

        when(this.hardwareControllerRepository.findAll()).thenReturn(java.util.List.of(hardwareControllerEntity));

        List<HardwareControllerEntity> result = this.hardwareControllerDAO.getAllHardwareControllers();

        assertSame(hardwareControllerEntity, result.get(0));
    }

    /**
     * Given a hardware controller id which is associated with a hardware controller
     * When the getHardwareController method is called with the hardware controller id
     * Then the hardwareControllerRepository.findById method is called with the hardwareControllerId
     * And the result of the hardwareControllerRepository.findById method is returned
     */
    @Test
    public void getHardwareController_whenControllerIdIsValid_thenReturnTheHardwareController(){
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        when(this.hardwareControllerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(hardwareControllerEntity));

        HardwareControllerEntity result = this.hardwareControllerDAO.getHardwareController(1L);

        assertSame(hardwareControllerEntity, result);
    }

    /**
     * Given a hardware controller id which is not associated with a hardware controller
     * When the getHardwareController method is called with the hardware controller id
     * Then the hardwareControllerRepository.findById method is called with the hardwareControllerId and returns an empty optional
     * Then the getHardwareController method throws a NotFoundException
     */
    @Test
    public void getHardwareController_whenControllerIdIsNotValid_thenReturnNull(){
        NotFoundException exception = new NotFoundException("Hardware", 1);

        when(this.hardwareControllerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(this.exceptionService.createNotFoundException(any(), anyLong())).thenReturn(exception);

        assertThrows(NotFoundException.class, () -> this.hardwareControllerDAO.getHardwareController(1L));
    }

    @Test
    void exists_returnsTrue_whenHardwareControllerExists() {
        String serialNumber = "1234";
        when(this.hardwareControllerRepository.existsBySerialNumber(eq(serialNumber))).thenReturn(true);
        boolean result = this.hardwareControllerDAO.exists(serialNumber);
        assertTrue(result);
    }

    @Test
    void exists_returnsFalse_whenHardwareControllerDoesntExist() {
        String serialNumber = "1234";
        when(this.hardwareControllerRepository.existsBySerialNumber(eq(serialNumber))).thenReturn(false);
        boolean result = this.hardwareControllerDAO.exists(serialNumber);
        assertFalse(result);
    }

    /**
     * Given a valid HardwareController object
     * When the updateHardwareController method is called
     * then the hardwareControllerRepository.findById method is called with the hardwareControllerId and returns the current hardwareControllerEntity
     * Then the hardwareControllerRepository.save method is called with the HardwareController object
     * And the result of the hardwareControllerRepository.save method is returned
     */
    @Test
    void updateHardwareController() {
        HardwareController hardwareController = new HardwareController();
        hardwareController.setId(1L);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        HardwareControllerEntity resultHardwareControllerEntity = new HardwareControllerEntity();

        when(this.hardwareControllerRepository.findById(anyLong())).thenReturn(Optional.of(hardwareControllerEntity));
        when(this.hardwareControllerRepository.save(any(HardwareControllerEntity.class))).thenReturn(resultHardwareControllerEntity);

        HardwareControllerEntity result = this.hardwareControllerDAO.updateHardwareController(hardwareController);

        assertSame(resultHardwareControllerEntity, result);
    }

    /**
     * Given a hardware controller id which is associated with a hardware controller
     * When the deleteHardwareController method is called with the hardware controller id
     * Then the hardwareControllerRepository.delete method is called with the hardwareControllerEntity
     */
    @Test
    public void deleteHardwareController_whenControllerIdIsValid_thenDeleteTheHardwareController(){
        long hardwareControllerId = 1L;

        when(this.hardwareControllerRepository.existsById(anyLong())).thenReturn(true);
        this.hardwareControllerDAO.delete(1L);
        verify(this.hardwareControllerRepository, times(1)).deleteById(same(hardwareControllerId));
    }

    /**
     * Given a hardware controller id which is not associated with a hardware controller
     * When the deleteHardwareController method is called with the hardware controller id
     * Then the hardwareControllerRepository.existsById method is called with the hardwareControllerId and returns false
     * Then the deleteHardwareController method throws a NotFoundException
     */
    @Test
    public void deleteHardwareController_whenControllerIdIsNotValid_thenThrowNotFoundException(){
        long hardwareControllerId = 1L;
        NotFoundException exception = new NotFoundException("Hardware", hardwareControllerId);

        when(this.hardwareControllerRepository.existsById(anyLong())).thenReturn(false);
        when(this.exceptionService.createNotFoundException(any(), anyLong())).thenReturn(exception);

        assertThrows(NotFoundException.class, () -> this.hardwareControllerDAO.delete(hardwareControllerId));
    }
}
