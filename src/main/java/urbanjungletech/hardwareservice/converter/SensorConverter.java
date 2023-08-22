package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.entity.SensorEntity;

import java.util.List;

public interface SensorConverter {
    Sensor toModel(SensorEntity sensorEntity);
    List<Sensor> toModels(List<SensorEntity> sensorEntities);
    void fillEntity(SensorEntity sensorEntity, Sensor sensor);
}
