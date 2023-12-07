package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.event.condition.ConditionEventPublisher;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.service.query.AlertConditionQueryService;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

import java.util.List;
import java.util.Map;

@Service
public class AlertConditionAdditionServiceImpl implements AlertConditionAdditionService {

    private final AlertConditionDAO alertConditionDAO;
    private final AlertConditionConverter alertConditionConverter;
    private final Map<AlertCondition, Alert> conditionAlertMap;
    private final AlertQueryService alertQueryService;
    private final ConditionEventPublisher conditionEventPublisher;
    private final AlertConditionQueryService alertConditionQueryService;

    public AlertConditionAdditionServiceImpl(AlertConditionDAO alertConditionDAO,
                                             AlertConditionConverter alertConditionConverter,
                                             Map<AlertCondition, Alert> conditionAlertMap,
                                             AlertQueryService alertQueryService,
                                             ConditionEventPublisher conditionEventPublisher,
                                             AlertConditionQueryService alertConditionQueryService){
        this.alertConditionDAO = alertConditionDAO;
        this.alertConditionConverter = alertConditionConverter;
        this.conditionAlertMap = conditionAlertMap;
        this.alertQueryService = alertQueryService;
        this.conditionEventPublisher = conditionEventPublisher;
        this.alertConditionQueryService = alertConditionQueryService;
    }
    @Override
    public AlertCondition create(AlertCondition alertCondition) {
        AlertConditionEntity alertConditionEntity = this.alertConditionDAO.create(alertCondition);
        AlertCondition result = this.alertConditionConverter.toModel(alertConditionEntity);
        Alert alert = this.alertQueryService.getSensorReadingAlert(result.getAlertId());
        this.conditionAlertMap.put(result, alert);
        return result;
    }

    @Override
    public void delete(long id) {
        this.alertConditionDAO.delete(id);
    }

    @Override
    public AlertCondition update(long id, AlertCondition condition) {
        condition.setId(id);
        AlertCondition existingCondition = this.alertConditionQueryService.getAlertCondition(id);
        if(existingCondition.getActive() != condition.getActive()){
            conditionEventPublisher.publishConditionActiveEvent(condition.getId());
        }
        AlertConditionEntity alertConditionEntity = this.alertConditionDAO.update(condition);
        this.conditionEventPublisher.publishUpdateConditionEvent(condition.getId());
        return this.alertConditionConverter.toModel(alertConditionEntity);
    }

    @Override
    public List<AlertCondition> updateList(List<AlertCondition> models) {
        return models.stream().map(model -> {
                    if (model.getId() == null) {
                        return this.create(model);
                    } else {
                        return this.update(model.getId(), model);
                    }
                }
        ).collect(java.util.stream.Collectors.toList());
    }
}
