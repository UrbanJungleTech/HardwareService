package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface AlertAdditionService extends AdditionService<Alert>{
    AlertAction addAction(long sensorReadingAlertId, AlertAction alertAction);
}
