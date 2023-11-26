package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.converter.implementation.SensorReadingConverterImpl;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SensorEntityReadingConverterImplTest {

    SensorReadingConverter sensorReadingConverter;

    @BeforeEach
    public void setup(){
        this.sensorReadingConverter = new SensorReadingConverterImpl();
    }

    @Test
    void toModel() {
        SensorReadingEntity sensorReadingEntity = new SensorReadingEntity();
        sensorReadingEntity.setId(1L);
        sensorReadingEntity.setReading(1.0);
        sensorReadingEntity.setReadingTime(LocalDateTime.MAX);
        SensorReading result = this.sensorReadingConverter.toModel(sensorReadingEntity);
        assertEquals(sensorReadingEntity.getId(), result.getId());
        assertEquals(sensorReadingEntity.getReading(), result.getReading());
        assertEquals(sensorReadingEntity.getReadingTime(), result.getReadingTime());
    }

    @Test
    void toModels() {
    }
}
