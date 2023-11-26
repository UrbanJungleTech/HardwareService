package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.List;

public interface AlertActionDAO {
    AlertActionEntity create(AlertAction alertAction);
    void deleteAction(long actionId);
    AlertAction updateAction(AlertAction alertAction);

    AlertActionEntity getAction(long actionId);

    List<AlertActionEntity> getAllActions();
}
