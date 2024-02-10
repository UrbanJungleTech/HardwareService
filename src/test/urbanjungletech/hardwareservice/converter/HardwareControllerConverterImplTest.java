package urbanjungletech.hardwareservice.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.hardware.HardwareConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation.HardwareControllerConverterImpl;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller.MockHardwareController;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Mock
    private Map<Class, SpecificHardwareControllerConverter> specificHardwareControllerConverters;
    @Mock
    private SpecificHardwareControllerConverter specificHardwareControllerConverter;

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
        when(specificHardwareControllerConverters.get(any())).thenReturn(specificHardwareControllerConverter);
        when(specificHardwareControllerConverter.toModel(any())).thenReturn(new MockHardwareController());
        HardwareController result = this.hardwareControllerConverter.toModel(hardwareControllerEntity);
        assertEquals("test", result.getName());
        assertEquals(1L, result.getId());
        assertSame(sensors, result.getSensors());
        assertSame(hardwares, result.getHardware());
    }

    @Test
    public void testToModelCallsSensorToModels(){
        HardwareControllerEntity hardwareController = new HardwareControllerEntity();
        when(specificHardwareControllerConverters.get(any())).thenReturn(specificHardwareControllerConverter);
        when(specificHardwareControllerConverter.toModel(any())).thenReturn(new MockHardwareController());
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
        when(specificHardwareControllerConverters.get(any())).thenReturn(specificHardwareControllerConverter);
        when(specificHardwareControllerConverter.toModel(any())).thenReturn(new MockHardwareController());
        HardwareController result = this.hardwareControllerConverter.toModel(hardwareControllerEntity);
        assertSame(hardwareResult, result.getHardware());
    }


    @Test
    void fillEntity() {
    }
}
