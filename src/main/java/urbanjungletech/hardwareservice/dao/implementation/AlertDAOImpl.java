package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConverter;
import urbanjungletech.hardwareservice.dao.AlertDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.AlertRepository;

import java.util.List;

@Service
public class AlertDAOImpl implements AlertDAO {

    private final AlertRepository alertRepository;
    private final AlertConverter alertConverter;
    private final ExceptionService exceptionService;
    private final ScheduledSensorReadingRepository scheduledSensorReadingRepository;

    public AlertDAOImpl(AlertRepository alertRepository,
                        AlertConverter alertConverter,
                        ExceptionService exceptionService,
                        ScheduledSensorReadingRepository scheduledSensorReadingRepository){
        this.alertRepository = alertRepository;
        this.alertConverter = alertConverter;
        this.exceptionService = exceptionService;
        this.scheduledSensorReadingRepository = scheduledSensorReadingRepository;
    }

    @Override
    public AlertEntity createAlert(Alert alert) {
        AlertEntity alertEntity = new AlertEntity();
        this.alertConverter.fillEntity(alertEntity, alert);
        AlertEntity result = this.alertRepository.save(alertEntity);
        this.alertRepository.save(result);
        return result;
    }

    @Override
    public AlertEntity getAlert(long sensorReadingAlertId) {
        AlertEntity result = this.alertRepository.findById(sensorReadingAlertId)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(AlertEntity.class, sensorReadingAlertId));
        return result;
    }

    @Override
    public List<AlertEntity> getAlerts() {
        return this.alertRepository.findAll();
    }

    @Override
    public AlertEntity updateSensorReadingAlert(Alert alert) {
        AlertEntity alertEntity = this.getAlert(alert.getId());
        this.alertConverter.fillEntity(alertEntity, alert);
        AlertEntity result = this.alertRepository.save(alertEntity);
        return result;
    }

    @Override
    public void deleteSensorReadingAlert(long sensorReadingAlertId) {
        AlertEntity alertEntity = this.getAlert(sensorReadingAlertId);
        this.alertRepository.delete(alertEntity);
    }
}
