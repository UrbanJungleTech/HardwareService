package urbanjungletech.hardwareservice.converter.sensor;

import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.List;

public interface SensorConverter {
    Sensor toModel(SensorEntity sensorEntity);
    List<Sensor> toModels(List<SensorEntity> sensorEntities);
    void fillEntity(SensorEntity sensorEntity, Sensor sensor);
    SensorEntity createEntity(Sensor sensor);
}
