package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
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
    public AlertConditionsDAOImpl(AlertConditionsRepository alertConditionsRepository,
                                  AlertRepository alertRepository,
                                  ExceptionService exceptionService) {
        this.alertConditionsRepository = alertConditionsRepository;
        this.alertRepository = alertRepository;
        this.exceptionService = exceptionService;
    }
    @Override
    public AlertConditionsEntity createAlertConditions(AlertConditions alertConditions) {
        AlertEntity alertEntity = this.alertRepository.findById(alertConditions.getAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(Alert.class, alertConditions.getAlertId())
        );
        AlertConditionsEntity alertConditionsEntity = new AlertConditionsEntity();
        alertConditionsEntity.setAlert(alertEntity);
        alertConditionsEntity = this.alertConditionsRepository.save(alertConditionsEntity);
        alertEntity.setConditions(alertConditionsEntity);
        this.alertRepository.save(alertEntity);
        return alertConditionsEntity;
    }
}
