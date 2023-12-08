package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

public interface AlertConditionQueryService {

    AlertCondition getAlertCondition(Long conditionId);
}
