package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.Alert;

import java.util.List;

public interface AlertQueryService {
    Alert getSensorReadingAlert(long id);
    List<Alert> getAllSensorReadingAlerts();
}
