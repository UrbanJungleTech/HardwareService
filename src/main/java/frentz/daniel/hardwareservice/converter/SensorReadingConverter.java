package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import frentz.daniel.hardwareservice.client.model.SensorReading;

import java.util.List;

public interface SensorReadingConverter {
    SensorReading toModel(SensorReadingEntity sensorReadingEntity);
    List<SensorReading> toModels(List<SensorReadingEntity> sensorReadingEntities);
    void fillEntity(SensorReadingEntity sensorReadingEntity, SensorReading sensorReading);
}
