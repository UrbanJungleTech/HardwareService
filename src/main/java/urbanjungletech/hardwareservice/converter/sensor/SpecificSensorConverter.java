package urbanjungletech.hardwareservice.converter.sensor;

import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

public interface SpecificSensorConverter<SensorType extends Sensor,
        SensorEntityType extends SensorEntity> {
    SensorType toModel(SensorEntityType sensor);
    SensorEntityType createEntity(SensorType sensor);
    void fillEntity(SensorEntityType sensorEntity, SensorType sensor);
}
