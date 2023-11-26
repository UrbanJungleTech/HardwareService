package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.Alert;

public interface AlertAdditionService extends AdditionService<Alert>{
    AlertAction addAction(long sensorReadingAlertId, AlertAction alertAction);
}
