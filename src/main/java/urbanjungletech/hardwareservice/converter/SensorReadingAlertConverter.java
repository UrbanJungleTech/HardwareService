package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;

public interface SensorReadingAlertConverter {

    SensorReadingAlert toModel(SensorReadingAlertEntity sensorReadingAlertEntity);
    void fillEntity(SensorReadingAlertEntity sensorReadingAlertEntity, SensorReadingAlert sensorReadingAlert);
}
