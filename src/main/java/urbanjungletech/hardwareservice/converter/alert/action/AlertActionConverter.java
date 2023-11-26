package urbanjungletech.hardwareservice.converter.alert.action;

import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface AlertActionConverter {
    AlertAction toModel(AlertActionEntity alertActionEntity);

    void fillEntity(AlertActionEntity alertActionEntity, AlertAction alertAction);

    AlertActionEntity createEntity(AlertAction alertAction);
}
