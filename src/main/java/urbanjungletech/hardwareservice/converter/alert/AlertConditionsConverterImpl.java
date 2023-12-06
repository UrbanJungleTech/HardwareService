package urbanjungletech.hardwareservice.converter.alert;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlertConditionsConverterImpl implements AlertConditionsConverter{
    private AlertConditionConverter alertConditionConverter;

    public AlertConditionsConverterImpl(AlertConditionConverter alertConditionConverter){
        this.alertConditionConverter = alertConditionConverter;
    }

    @Override
    public AlertConditions toModel(AlertConditionsEntity alertConditionsEntity) {
        AlertConditions result = new AlertConditions();
        List<AlertCondition> conditions = alertConditionsEntity.getConditions()
                .stream().map(alertConditionConverter::toModel).toList();
        result.setConditions(conditions);
        Set<AlertCondition> alertActiveConditions = alertConditionsEntity.getActiveConditions()
                .stream().map(alertConditionConverter::toModel).collect(HashSet::new, HashSet::add, HashSet::addAll);
        result.setActiveConditions(alertActiveConditions);
        Set<AlertCondition> alertInactiveConditions = alertConditionsEntity.getInactiveConditions()
                .stream().map(alertConditionConverter::toModel).collect(HashSet::new, HashSet::add, HashSet::addAll);
        result.setInactiveConditions(alertInactiveConditions);
        return result;
    }

    @Override
    public void fillEntity(AlertConditions alertConditions, AlertConditionsEntity alertConditionsEntity) {

    }
}
