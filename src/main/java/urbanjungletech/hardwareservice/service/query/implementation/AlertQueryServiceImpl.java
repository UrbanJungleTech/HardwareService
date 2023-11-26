package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConverter;
import urbanjungletech.hardwareservice.dao.AlertDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

import java.util.List;

@Service
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertDAO alertDAO;
    private final AlertConverter alertConverter;

    public AlertQueryServiceImpl(AlertDAO alertDAO,
                                 AlertConverter alertConverter){
        this.alertDAO = alertDAO;
        this.alertConverter = alertConverter;
    }

    @Override
    public Alert getSensorReadingAlert(long id) {
        AlertEntity sensorReadingAlertEntities = this.alertDAO.getAlert(id);
        return this.alertConverter.toModel(sensorReadingAlertEntities);
    }

    @Override
    public List<Alert> getAllSensorReadingAlerts() {
        List<AlertEntity> sensorReadingAlertEntities = this.alertDAO.getAlerts();
        return sensorReadingAlertEntities.stream().map(this.alertConverter::toModel).toList();
    }
}
