package urbanjungletech.hardwareservice.converter.alert.condition.implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.Map;

@Service
public class AlertConditionConverterImpl implements AlertConditionConverter {

    private final Map<Class, SpecificAlertConditionConverter> actionConverterMap;

    public AlertConditionConverterImpl(@Qualifier("alertConditionConverterMappings") Map<Class, SpecificAlertConditionConverter> actionConverterMap) {
        this.actionConverterMap = actionConverterMap;
    }

    @Override
    public AlertCondition toModel(AlertConditionEntity alertConditionEntity) {
        AlertCondition result = this.actionConverterMap.get(alertConditionEntity.getClass()).toModel(alertConditionEntity);
        result.setId(alertConditionEntity.getId());
        result.setAlertId(alertConditionEntity.getAlert().getId());
        result.setActive(alertConditionEntity.isActive());
        return result;
    }

    @Override
    public void fillEntity(AlertConditionEntity alertConditionEntity, AlertCondition alertCondition) {
        this.actionConverterMap.get(alertCondition.getClass()).fillEntity(alertConditionEntity, alertCondition);
        alertConditionEntity.setType(alertCondition.getType());
        alertConditionEntity.setActive(alertCondition.isActive());
    }

    @Override
    public AlertConditionEntity createEntity(AlertCondition alertCondition) {
        AlertConditionEntity result = this.actionConverterMap.get(alertCondition.getClass()).createEntity(alertCondition);
        this.fillEntity(result, alertCondition);
        return result;
    }
}
