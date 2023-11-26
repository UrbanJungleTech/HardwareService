package urbanjungletech.hardwareservice.converter.alert.condition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.condition.HardwareStateChangeAlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;

@Service
public class HardwareStateChangeConditionConverter implements SpecificAlertConditionConverter<HardwareStateChangeAlertCondition, HardwareStateChangeAlertConditionEntity> {

    @Override
    public HardwareStateChangeAlertCondition toModel(HardwareStateChangeAlertConditionEntity alertConditionEntity) {
        HardwareStateChangeAlertCondition result = new HardwareStateChangeAlertCondition();
        result.setState(alertConditionEntity.getState());
        result.setHardwareId(alertConditionEntity.getHardwareId());
        return result;
    }

    @Override
    public void fillEntity(HardwareStateChangeAlertConditionEntity alertConditionEntity, HardwareStateChangeAlertCondition alertCondition) {
        alertConditionEntity.setState(alertCondition.getState());
        alertConditionEntity.setHardwareId(alertCondition.getHardwareId());
    }

    @Override
    public HardwareStateChangeAlertConditionEntity createEntity(HardwareStateChangeAlertCondition alertCondition) {
        HardwareStateChangeAlertConditionEntity result = new HardwareStateChangeAlertConditionEntity();
        fillEntity(result, alertCondition);
        return result;
    }
}
