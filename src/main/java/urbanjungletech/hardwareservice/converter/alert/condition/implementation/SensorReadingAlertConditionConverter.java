package urbanjungletech.hardwareservice.converter.alert.condition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.condition.SensorReadingAlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;

@Service
public class SensorReadingAlertConditionConverter implements SpecificAlertConditionConverter<SensorReadingAlertCondition, SensorReadingAlertConditionEntity> {
    @Override
    public SensorReadingAlertCondition toModel(SensorReadingAlertConditionEntity alertConditionEntity) {
        SensorReadingAlertCondition result = new SensorReadingAlertCondition();
        result.setThresholdType(alertConditionEntity.getThresholdType());
        result.setThreshold(alertConditionEntity.getThreshold());
        result.setSensorId(alertConditionEntity.getSensorId());
        return result;
    }

    @Override
    public void fillEntity(SensorReadingAlertConditionEntity alertConditionEntity, SensorReadingAlertCondition alertCondition) {
        alertConditionEntity.setThresholdType(alertCondition.getThresholdType());
        alertConditionEntity.setThreshold(alertCondition.getThreshold());
        alertConditionEntity.setSensorId(alertCondition.getSensorId());
    }

    @Override
    public SensorReadingAlertConditionEntity createEntity(SensorReadingAlertCondition alertCondition) {
        SensorReadingAlertConditionEntity result = new SensorReadingAlertConditionEntity();
        fillEntity(result, alertCondition);
        return result;
    }
}
