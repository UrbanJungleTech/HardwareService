package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConditionsConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionsDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.repository.AlertConditionsRepository;
import urbanjungletech.hardwareservice.repository.AlertRepository;

@Service
public class AlertConditionsDAOImpl implements AlertConditionsDAO {

    private final AlertConditionsRepository alertConditionsRepository;
    private final AlertRepository alertRepository;
    private final ExceptionService exceptionService;
    private final AlertConditionsConverter alertConditionsConverter;

    public AlertConditionsDAOImpl(AlertConditionsRepository alertConditionsRepository,
                                  AlertRepository alertRepository,
                                  ExceptionService exceptionService,
                                  AlertConditionsConverter alertConditionsConverter) {
        this.alertConditionsRepository = alertConditionsRepository;
        this.alertRepository = alertRepository;
        this.exceptionService = exceptionService;
        this.alertConditionsConverter = alertConditionsConverter;
    }
    @Override
    public AlertConditionsEntity createAlertConditions(AlertConditions alertConditions) {
        AlertEntity alertEntity = this.alertRepository.findById(alertConditions.getAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(Alert.class, alertConditions.getAlertId())
        );
        AlertConditionsEntity alertConditionsEntity = new AlertConditionsEntity();
        alertConditionsConverter.fillEntity(alertConditions, alertConditionsEntity);
        alertConditionsEntity.setAlert(alertEntity);
        alertConditionsEntity = this.alertConditionsRepository.save(alertConditionsEntity);
        alertEntity.setConditions(alertConditionsEntity);
        this.alertRepository.save(alertEntity);
        return alertConditionsEntity;
    }

    @Override
    public AlertConditionsEntity update(AlertConditions alertConditions) {
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsRepository.findById(alertConditions.getId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertConditions.class, alertConditions.getId())
        );
        this.alertConditionsConverter.fillEntity(alertConditions, alertConditionsEntity);
        this.alertConditionsRepository.save(alertConditionsEntity);
        return this.alertConditionsRepository.save(alertConditionsEntity);
    }

    @Override
    public void delete(long id) {
        this.alertConditionsRepository.deleteById(id);
    }
}
