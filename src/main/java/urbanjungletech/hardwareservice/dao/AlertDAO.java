package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;

import java.util.List;

public interface AlertDAO {
    AlertEntity createAlert(Alert alert);
    AlertEntity getAlert(long sensorReadingAlertId);
    List<AlertEntity> getAlerts();
    AlertEntity updateSensorReadingAlert(Alert alert);
    void deleteSensorReadingAlert(long sensorReadingAlertId);

}
