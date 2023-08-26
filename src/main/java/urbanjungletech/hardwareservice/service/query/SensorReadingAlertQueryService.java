package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.SensorReadingAlert;

import java.util.List;

public interface SensorReadingAlertQueryService {
    SensorReadingAlert getSensorReadingAlert(long id);
    List<SensorReadingAlert> getAllSensorReadingAlerts();
}
