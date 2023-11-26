package urbanjungletech.hardwareservice.converter.alert.condition;

import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

public interface SpecificAlertConditionConverter <Model extends AlertCondition, Entity extends AlertConditionEntity> {
    Model toModel(Entity alertConditionEntity);

    void fillEntity(Entity alertConditionEntity, Model alertCondition);

    Entity createEntity(Model alertCondition);
}
