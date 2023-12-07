package urbanjungletech.hardwareservice.service.alert.condition.service;

import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

public interface SpecificConditionTriggerService <ConditionType extends AlertCondition> {
    void trigger(Long hardwareStateChangeId);
}
