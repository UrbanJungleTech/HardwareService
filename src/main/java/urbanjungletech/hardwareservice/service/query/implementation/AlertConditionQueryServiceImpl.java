package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.service.query.AlertConditionQueryService;

@Service
public class AlertConditionQueryServiceImpl implements AlertConditionQueryService {

    private final AlertConditionRepository alertConditionRepository;
    private final AlertConditionConverter alertConditionConverter;
    private final ExceptionService exceptionService;

    public AlertConditionQueryServiceImpl(AlertConditionRepository alertConditionRepository,
                                            AlertConditionConverter alertConditionConverter,
                                            ExceptionService exceptionService) {
        this.alertConditionRepository = alertConditionRepository;
        this.alertConditionConverter = alertConditionConverter;
        this.exceptionService = exceptionService;
    }
    @Override
    public AlertCondition getAlertCondition(Long conditionId) {
        AlertConditionEntity alertConditionEntity = this.alertConditionRepository.findById(conditionId).orElseThrow(
                () -> this.exceptionService.createNotFoundException(AlertCondition.class, conditionId)
        );
        return this.alertConditionConverter.toModel(alertConditionEntity);
    }
}
