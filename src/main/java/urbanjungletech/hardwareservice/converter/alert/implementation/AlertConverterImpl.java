package urbanjungletech.hardwareservice.converter.alert.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConditionsConverter;
import urbanjungletech.hardwareservice.converter.alert.AlertConverter;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertConverterImpl implements AlertConverter {

    private final AlertActionConverter alertActionConverter;
    private AlertConditionsConverter alertConditionsConverter;

    public AlertConverterImpl(AlertActionConverter alertActionConverter,
                              AlertConditionsConverter alertConditionsConverter){
        this.alertActionConverter = alertActionConverter;
        this.alertConditionsConverter = alertConditionsConverter;
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
        AlertConditions alertConditions = this.alertConditionsConverter.toModel(alertEntity.getConditions());
        result.setConditions(alertConditions);
        return result;
    }

    @Override
    public void fillEntity(AlertEntity alertEntity, Alert alert) {
        alertEntity.setName(alert.getName());
    }
}
