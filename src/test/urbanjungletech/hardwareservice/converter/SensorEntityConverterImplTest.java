package urbanjungletech.hardwareservice.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SensorEntityConverterImplTest {


    @Mock
    ScheduledSensorReadingConverter scheduledSensorReadingConverter;

    @InjectMocks
    private SensorConverter sensorConverter;


    @Test
    void toModel() {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId(1L);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setId(2L);
        sensorEntity.setHardwareController(hardwareControllerEntity);
        sensorEntity.setPort("3");
        Sensor result = this.sensorConverter.toModel(sensorEntity);
        assertEquals(sensorEntity.getId(), result.getId());
        assertEquals(sensorEntity.getPort(), result.getPort());
        assertEquals(sensorEntity.getHardwareController().getId(), result.getHardwareControllerId());
    }

    @Test
    void toModels() {
    }
}
