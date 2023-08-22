package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.SensorReadingAlert;

import java.util.List;

public interface SensorReadingAlertService {
    SensorReadingAlert getSensorReadingAlert(long id);
    List<SensorReadingAlert> getAllSensorReadingAlerts();
}
