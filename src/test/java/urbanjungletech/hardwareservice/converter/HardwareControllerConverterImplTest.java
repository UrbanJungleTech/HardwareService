package urbanjungletech.hardwareservice.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.implementation.HardwareControllerConverterImpl;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        hardwareControllerEntity.getConfiguration().put("serialNumber", "1234");
        List<Sensor> sensors = new ArrayList<>();
        List<Hardware> hardwares = new ArrayList<>();
        when(sensorConverter.toModels(any())).thenReturn(sensors);
        when(hardwareConverter.toModels(any())).thenReturn(hardwares);
        HardwareController result = this.hardwareControllerConverter.toModel(hardwareControllerEntity);
        assertEquals("test", result.getName());
        assertEquals("1234", result.getConfiguration().get("serialNumber"));
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
