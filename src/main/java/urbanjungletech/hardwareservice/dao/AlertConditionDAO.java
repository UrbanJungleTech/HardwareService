package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

public interface AlertConditionDAO {
    AlertConditionEntity create(AlertCondition alertCondition);
}
