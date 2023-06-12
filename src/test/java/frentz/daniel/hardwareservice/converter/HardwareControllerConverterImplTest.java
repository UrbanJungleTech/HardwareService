package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HardwareControllerConverterImplTest {

    @Mock
    private HardwareConverter hardwareConverter;

    @Mock
    private SensorConverter sensorConverter;

    @InjectMocks
    private HardwareControllerConverterImpl hardwareControllerConverter;

    @Test
    void toModel() {
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setName("test");
        hardwareControllerEntity.setId(1L);
        hardwareControllerEntity.setSerialNumber("1234");
        List<Sensor> sensors = new ArrayList<>();
        List<Hardware> hardwares = new ArrayList<>();
        when(sensorConverter.toModels(any())).thenReturn(sensors);
        when(hardwareConverter.toModels(any())).thenReturn(hardwares);
        HardwareController result = this.hardwareControllerConverter.toModel(hardwareControllerEntity);
        assertEquals("test", result.getName());
        assertEquals("1234", result.getSerialNumber());
        assertEquals(1L, result.getId());
        assertSame(sensors, result.getSensors());
        assertSame(hardwares, result.getHardware());
    }

    @Test
    public void testToModelCallsSensorToModels(){
        HardwareControllerEntity hardwareController = new HardwareControllerEntity();
        this.hardwareControllerConverter.toModel(hardwareController);
        List sensors = new ArrayList();
        hardwareController.setSensors(sensors);
        List<Sensor> sensorResult = new ArrayList<>();
        when(sensorConverter.toModels(sensors)).thenReturn(sensorResult);
        HardwareController result = hardwareControllerConverter.toModel(hardwareController);
        assertSame(sensorResult, result.getSensors());
    }

    @Test
    public void testToModelCallsHardwareToModels(){
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        List<HardwareEntity> hardwareEntities = new ArrayList<>();
        List<Hardware> hardwareResult = new ArrayList<>();
        when(hardwareConverter.toModels(hardwareEntities)).thenReturn(hardwareResult);
        hardwareControllerEntity.setHardware(hardwareEntities);
        HardwareController result = this.hardwareControllerConverter.toModel(hardwareControllerEntity);
        assertSame(hardwareResult, result.getHardware());
    }


    @Test
    void fillEntity() {
    }
}
