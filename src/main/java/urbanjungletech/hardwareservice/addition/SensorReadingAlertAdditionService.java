package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.action.model.Action;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;

public interface SensorReadingAlertAdditionService extends AdditionService<SensorReadingAlert>{
    Action addAction(long sensorReadingAlertId, Action action);
}
