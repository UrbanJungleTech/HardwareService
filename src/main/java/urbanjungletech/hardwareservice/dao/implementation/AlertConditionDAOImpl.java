package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.repository.AlertConditionsRepository;
import urbanjungletech.hardwareservice.repository.AlertRepository;

@Service
public class AlertConditionDAOImpl implements AlertConditionDAO {

    private final AlertConditionRepository alertConditionRepository;
    private final AlertConditionConverter alertConditionConverter;
    private final AlertRepository alertRepository;
    private final ExceptionService exceptionService;
    private final AlertConditionsRepository alertConditionsRepository;

    public AlertConditionDAOImpl(AlertConditionRepository alertConditionRepository,
                                 AlertConditionConverter alertConditionConverter,
                                 AlertRepository alertRepository,
                                 ExceptionService exceptionService,
                                 AlertConditionsRepository alertConditionsRepository) {
        this.alertConditionRepository = alertConditionRepository;
        this.alertConditionConverter = alertConditionConverter;
        this.alertRepository = alertRepository;
        this.exceptionService = exceptionService;
        this.alertConditionsRepository = alertConditionsRepository;
    }
    @Override
    public AlertConditionEntity create(AlertCondition alertCondition) {
        AlertConditionEntity alertConditionEntity = this.alertConditionConverter.createEntity(alertCondition);
        AlertEntity alertEntity = this.alertRepository.findById(alertCondition.getAlertId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertEntity.class, alertCondition.getAlertId())
        );
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsRepository.findById(alertEntity.getConditions().getId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertConditionsEntity.class, alertEntity.getConditions().getId())
        );
        alertConditionEntity.setAlert(alertEntity);
        alertEntity.setConditions(alertConditionsEntity);
        AlertConditionEntity result = this.alertConditionRepository.save(alertConditionEntity);

        alertEntity.getConditions().getAllConditions().add(result);
        this.alertConditionsRepository.save(alertConditionsEntity);
        return result;
    }

    @Override
    public AlertConditionEntity update(AlertCondition condition) {
        AlertConditionEntity alertConditionEntity = this.alertConditionRepository.findById(condition.getId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertConditionEntity.class, condition.getId())
        );
        this.alertConditionConverter.fillEntity(alertConditionEntity, condition);
        AlertConditionEntity result = this.alertConditionRepository.save(alertConditionEntity);
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsRepository.findById(alertConditionEntity.getAlert().getConditions().getId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertConditionsEntity.class, alertConditionEntity.getAlert().getConditions().getId())
        );
        this.alertConditionsRepository.save(alertConditionsEntity);
        return result;
    }

    @Override
    public void delete(long id) {
        AlertConditionEntity alertConditionEntity = this.alertConditionRepository.findById(id).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertConditionEntity.class, id)
        );
        this.alertConditionRepository.delete(alertConditionEntity);
    }
}
