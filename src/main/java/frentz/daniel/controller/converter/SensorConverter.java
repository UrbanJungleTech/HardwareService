package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controllerclient.model.Sensor;

import java.util.List;

public interface SensorConverter {
    Sensor toModel(SensorEntity sensorEntity);
    List<Sensor> toModels(List<SensorEntity> sensorEntities);
}
