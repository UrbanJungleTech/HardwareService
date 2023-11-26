package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.repository.AlertRepository;

@Service
public class AlertConditionDAOImpl implements AlertConditionDAO {

    private final AlertConditionRepository alertConditionRepository;
    private final AlertConditionConverter alertConditionConverter;
    private final AlertRepository alertRepository;
    private final ExceptionService exceptionService;

    public AlertConditionDAOImpl(AlertConditionRepository alertConditionRepository,
                                 AlertConditionConverter alertConditionConverter,
                                 AlertRepository alertRepository,
                                 ExceptionService exceptionService) {
        this.alertConditionRepository = alertConditionRepository;
        this.alertConditionConverter = alertConditionConverter;
        this.alertRepository = alertRepository;
        this.exceptionService = exceptionService;
    }
    @Override
    public AlertConditionEntity create(AlertCondition alertCondition) {
        AlertConditionEntity alertConditionEntity = this.alertConditionConverter.createEntity(alertCondition);
        AlertEntity alertEntity = this.alertRepository.findById(alertCondition.getAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertEntity.class, alertCondition.getAlertId())
        );
        alertConditionEntity.setAlert(alertEntity);
        AlertConditionEntity result = this.alertConditionRepository.save(alertConditionEntity);

        alertEntity.getConditions().add(alertConditionEntity);
        this.alertRepository.save(alertEntity);
        return result;
    }
}
