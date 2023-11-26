package urbanjungletech.hardwareservice.converter.alert;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertConverterImpl implements AlertConverter {

    private final AlertActionConverter alertActionConverter;
    private final AlertConditionConverter alertConditionConverter;

    public AlertConverterImpl(AlertActionConverter alertActionConverter,
                              AlertConditionConverter alertConditionConverter){
        this.alertActionConverter = alertActionConverter;
        this.alertConditionConverter = alertConditionConverter;
    }
    @Override
    public Alert toModel(AlertEntity alertEntity) {
        Alert result = new Alert();
        result.setId(alertEntity.getId());
        result.setName(alertEntity.getName());
        result.setDescription(alertEntity.getDescription());
        Optional.ofNullable(alertEntity.getActions()).ifPresent(actions -> {
            List<AlertAction> alertActionList = actions.stream()
                    .map(alertActionConverter::toModel)
                    .collect(Collectors.toList());
            result.setActions(alertActionList);
        });
        Optional.ofNullable(alertEntity.getConditions()).ifPresent(conditions -> {
            List<AlertCondition> alertConditionList = conditions.stream()
                    .map(alertConditionConverter::toModel)
                    .collect(Collectors.toList());
            result.setConditions(alertConditionList);
        });
        return result;
    }

    @Override
    public void fillEntity(AlertEntity alertEntity, Alert alert) {
        alertEntity.setName(alert.getName());
    }
}
