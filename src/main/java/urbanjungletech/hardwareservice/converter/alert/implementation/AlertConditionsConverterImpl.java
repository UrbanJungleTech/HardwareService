package urbanjungletech.hardwareservice.converter.alert.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConditionsConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlertConditionsConverterImpl implements AlertConditionsConverter {
    private AlertConditionConverter alertConditionConverter;

    public AlertConditionsConverterImpl(AlertConditionConverter alertConditionConverter){
        this.alertConditionConverter = alertConditionConverter;
    }

    @Override
    public AlertConditions toModel(AlertConditionsEntity alertConditionsEntity) {
        AlertConditions result = new AlertConditions();
        List<AlertCondition> conditions = alertConditionsEntity.getAllConditions()
                .stream().map(alertConditionConverter::toModel).toList();
        result.setConditions(conditions);
        for(AlertConditionEntity conditionEntity : alertConditionsEntity.getAllConditions()){
            if(conditionEntity.isActive()){
                result.getActiveConditions().add(alertConditionConverter.toModel(conditionEntity));
            }
            else{
                result.getInactiveConditions().add(alertConditionConverter.toModel(conditionEntity));
            }
        }
        result.setId(alertConditionsEntity.getId());
        result.setAlertId(alertConditionsEntity.getAlert().getId());
        return result;
    }

    @Override
    public void fillEntity(AlertConditions alertConditions, AlertConditionsEntity alertConditionsEntity) {

    }
}
