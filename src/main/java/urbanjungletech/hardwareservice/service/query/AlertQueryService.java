package urbanjungletech.hardwareservice.service.query;

import org.springframework.data.jpa.repository.Query;
import urbanjungletech.hardwareservice.model.alert.Alert;

import java.util.List;

public interface AlertQueryService {
    Alert getSensorReadingAlert(long id);
    List<Alert> getAllSensorReadingAlerts();
}
