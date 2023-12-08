package urbanjungletech.hardwareservice.converter.alert;

import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;

public interface AlertConditionsConverter {
    AlertConditions toModel(AlertConditionsEntity alertConditionsEntity);
    void fillEntity(AlertConditions alertConditions, AlertConditionsEntity alertConditionsEntity);
}
