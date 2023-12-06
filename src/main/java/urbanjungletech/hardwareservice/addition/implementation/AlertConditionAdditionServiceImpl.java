package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionDAO;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

import java.util.List;
import java.util.Map;

@Service
public class AlertConditionAdditionServiceImpl implements AlertConditionAdditionService {

    private final AlertConditionDAO alertConditionDAO;
    private final AlertConditionConverter alertConditionConverter;
    private final Map<AlertCondition, Alert> conditionAlertMap;
    private final AlertQueryService alertQueryService;

    public AlertConditionAdditionServiceImpl(AlertConditionDAO alertConditionDAO,
                                             AlertConditionConverter alertConditionConverter,
                                             Map<AlertCondition, Alert> conditionAlertMap,
                                             AlertQueryService alertQueryService){
        this.alertConditionDAO = alertConditionDAO;
        this.alertConditionConverter = alertConditionConverter;
        this.conditionAlertMap = conditionAlertMap;
        this.alertQueryService = alertQueryService;
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

    }

    @Override
    public AlertCondition update(long id, AlertCondition alertCondition) {
        return null;
    }

    @Override
    public List<AlertCondition> updateList(List<AlertCondition> models) {
        return null;
    }
}
