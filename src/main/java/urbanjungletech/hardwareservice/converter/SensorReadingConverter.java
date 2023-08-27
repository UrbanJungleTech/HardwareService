package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;

import java.util.List;

public interface SensorReadingConverter {
    SensorReading toModel(SensorReadingEntity sensorReadingEntity);
    List<SensorReading> toModels(List<SensorReadingEntity> sensorReadingEntities);
    void fillEntity(SensorReadingEntity sensorReadingEntity, SensorReading sensorReading);
}
