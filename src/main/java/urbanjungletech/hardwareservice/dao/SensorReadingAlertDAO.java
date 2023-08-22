package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;

import java.util.List;

public interface SensorReadingAlertDAO {
    SensorReadingAlertEntity createSensorReadingAlert(SensorReadingAlert sensorReadingAlert);
    SensorReadingAlertEntity getSensorReadingAlert(long sensorReadingAlertId);
    List<SensorReadingAlertEntity> getSensorReadingAlerts();
    SensorReadingAlertEntity updateSensorReadingAlert(SensorReadingAlert sensorReadingAlert);
    void deleteSensorReadingAlert(long sensorReadingAlertId);

}
