package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.client.model.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SensorEntityConverterImplTest {


    @Mock
    ScheduledSensorReadingConverter scheduledSensorReadingConverter;

    private SensorConverter sensorConverter;

    @BeforeEach
    public void setup(){
        this.sensorConverter = new SensorConverterImpl(scheduledSensorReadingConverter);
    }

    @Test
    void toModel() {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId(1L);
        sensorEntity.setSensorType(SensorType.HUMIDITY);
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
        hardwareControllerEntity.setId(2L);
        sensorEntity.setHardwareController(hardwareControllerEntity);
        sensorEntity.setPort(3);
        frentz.daniel.controllerclient.model.Sensor result = this.sensorConverter.toModel(sensorEntity);
        assertEquals(sensorEntity.getId(), result.getId());
        assertEquals(sensorEntity.getPort(), result.getPort());
        assertEquals(sensorEntity.getSensorType(), result.getSensorType());
        assertEquals(sensorEntity.getHardwareController().getId(), result.getHardwareControllerId());
    }

    @Test
    void toModels() {
    }
}
