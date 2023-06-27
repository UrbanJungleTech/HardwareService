package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.converter.HardwareControllerConverter;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardwareControllerServiceImplTest {

    @Mock
    private HardwareDAO hardwareDAO;
    @Mock
    private HardwareControllerDAO hardwareControllerDAO;
    @Mock
    private HardwareControllerConverter hardwareControllerConverter;
    @Mock
    private SensorDAO sensorDAO;
    @Mock
    private SensorConverter sensorConverter;
    @Mock
    private HardwareConverter hardwareConverter;

    @InjectMocks
    private HardwareControllerServiceImpl hardwareControllerService;

    /**
     * Given a hardware controller id that is associated with a hardware controller entity and a hardware entity is associated with the hardware controller entity
     * When getHardware is called with the hardware controller id
     * Then the hardwareDAO should be called with the hardware controller id
     * And the hardwareConverter.toModels should be called with the hardware entities returned from the hardwareDAO
     * And the hardwareConverter should return a list of hardware which was returned by the hardware converter
     */
    @Test
    void getHardware_whenHardwareIdIsAssociatedWithAHardwareEntity_shouldReturnHardware() {
        long hardwareControllerId = 1l;
        List<HardwareEntity> hardwareEntities = List.of(new HardwareEntity());
        List<Hardware> expected = List.of(new Hardware());

        when(this.hardwareDAO.getHardwareByHardwareControllerId(same(hardwareControllerId))).thenReturn(hardwareEntities);
        when(this.hardwareConverter.toModels(same(hardwareEntities))).thenReturn(expected);

        List<Hardware> result = this.hardwareControllerService.getHardware(hardwareControllerId);

        assertEquals(expected, result);
    }

    /**
     * Given a hardware controller id that is associated with a hardware controller id.
     * When getHardwareController.getHardware is called with the hardware controller id
     * Then the hardwareDAO should be called with the hardware controller id
     * Then the hardwareDAO should throw a NotFoundException
     */
    @Test
    void getHardware_whenHardwareIdIsAssociatedWithAHardwareControllerEntity_shouldThrowNotFoundException() {
        long hardwareControllerId = 1l;

        when(this.hardwareDAO.getHardwareByHardwareControllerId(same(hardwareControllerId))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> this.hardwareControllerService.getHardware(hardwareControllerId));
    }

    /**
     * Given a HardwareController id which is not associated with a hardware controller
     * When getHardware is called with the hardware controller id
     * Then the hardwareDAO should be called with the hardware controller id
     * And the hardwareDAO should throw a NotFoundException
     * And the call to hardwareControllerService.getHardware should throw a NotFoundException
     */
    @Test
    void getHardware_whenHardwareIdIsNotAssociatedWithAHardwareEntity_shouldThrowNotFoundException() {
        long hardwareControllerId = 1l;

        when(this.hardwareDAO.getHardwareByHardwareControllerId(same(hardwareControllerId))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> this.hardwareControllerService.getHardware(hardwareControllerId));
    }

    /**
     * Given a HardwareController id which is associated with a hardware controller entity
     * When getHardwareController is called with the hardware controller id
     * Then the hardwareControllerDAO should be called with the hardware controller id
     * And the hardwareControllerConverter.toModel should be called with the hardware controller entity returned from the hardwareControllerDAO
     * And the hardwareControllerService.getHardwareController should return the hardware controller returned by the hardwareControllerConverter
     */
    @Test
    void getHardwareController_whenHardwareControllerIdIsAssociatedWithAHardwareControllerEntity_shouldReturnHardwareController() {
        long hardwareControllerId = 1l;
        HardwareController expected = new HardwareController();
        HardwareControllerEntity savedHardwareController = new HardwareControllerEntity();

        when(this.hardwareControllerDAO.getHardwareController(same(hardwareControllerId))).thenReturn(savedHardwareController);
        when(this.hardwareControllerConverter.toModel(same(savedHardwareController))).thenReturn(expected);

        HardwareController result = this.hardwareControllerService.getHardwareController(hardwareControllerId);

        assertEquals(expected, result);
    }

    /**
     * Given 1 HardwareController has been saved to the database
     * When getAllHardwareControllers is called on the hardwareControllerService
     * Then the hardwareControllerDAO.getAllHardwareControllers should be called
     * And the hardwareControllerConverter.toModels should be called with the hardware controller entities returned from the hardwareControllerDAO
     * And the hardwareControllerService.getAllHardwareControllers should return a list of hardware controllers which was returned by the hardwareControllerConverter
     */
    @Test
    void getAllHardwareControllers_whenHardwareControllerHasBeenSaved_shouldReturnHardwareController() {
        List<HardwareControllerEntity> savedHardwareControllers = List.of(new HardwareControllerEntity());
        List<HardwareController> expected = List.of(new HardwareController());

        when(this.hardwareControllerDAO.getAllHardwareControllers()).thenReturn(savedHardwareControllers);
        when(this.hardwareControllerConverter.toModels(same(savedHardwareControllers))).thenReturn(expected);

        List<HardwareController> result = this.hardwareControllerService.getAllHardwareControllers();

        verify(this.hardwareControllerDAO).getAllHardwareControllers();
        verify(this.hardwareControllerConverter).toModels(same(savedHardwareControllers));
        assertEquals(expected, result);
    }


    /**
     * Given a hardware controller id that is associated with a hardware controller entity
     * When hardwareControllerService.getSensors is called with the hardware controller id
     * Then the sensorDAO should be called with the hardware controller id
     * And the sensorConverter.toModels should be called with the sensor entities returned from the sensorDAO
     * And the hardwareControllerService.getHardwareController.getSensors should return a list of sensors which was returned by the sensorConverter
     */
    @Test
    void getHardwareControllerSensors_whenHardwareControllerIdIsAssociatedWithAHardwareControllerEntity_shouldReturnHardwareControllerSensors() {
        long hardwareControllerId = 1l;
        List<SensorEntity> sensorEntities = List.of(new SensorEntity());
        List<Sensor> expected = List.of(new Sensor());

        when(this.sensorDAO.getSensorsByHardwareControllerId(same(hardwareControllerId))).thenReturn(sensorEntities);
        when(this.sensorConverter.toModels(same(sensorEntities))).thenReturn(expected);

        List<Sensor> result = this.hardwareControllerService.getSensors(hardwareControllerId);

        assertEquals(expected, result);
    }

    /**
     * Given a hardware controller id that is not associated with a hardware controller entity
     * When getHardwareController.getSensors is called with the hardware controller id
     * Then the sensorDAO should throw a NotFoundException
     * And the hardwareControllerService.getHardwareController.getSensors should throw a NotFoundException
     */
    @Test
    void getHardwareControllerSensors_whenHardwareControllerIdIsNotAssociatedWithAHardwareControllerEntity_shouldThrowNotFoundException() {
        long hardwareControllerId = 1l;

        when(this.sensorDAO.getSensorsByHardwareControllerId(same(hardwareControllerId))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> this.hardwareControllerService.getSensors(hardwareControllerId));
    }
}
