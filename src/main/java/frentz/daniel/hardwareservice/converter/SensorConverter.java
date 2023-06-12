package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.entity.SensorEntity;

import java.util.List;

public interface SensorConverter {
    Sensor toModel(SensorEntity sensorEntity);
    List<Sensor> toModels(List<SensorEntity> sensorEntities);
    void fillEntity(SensorEntity sensorEntity, Sensor sensor);
}
