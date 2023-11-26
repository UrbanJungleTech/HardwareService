package urbanjungletech.hardwareservice.converter.alert.condition;

import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

public interface AlertConditionConverter {
    AlertCondition toModel(AlertConditionEntity alertConditionEntity);
    void fillEntity(AlertConditionEntity alertConditionEntity, AlertCondition alertCondition);
    AlertConditionEntity createEntity(AlertCondition alertCondition);
}
