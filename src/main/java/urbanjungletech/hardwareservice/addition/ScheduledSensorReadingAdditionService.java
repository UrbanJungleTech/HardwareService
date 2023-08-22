package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;

public interface ScheduledSensorReadingAdditionService extends AdditionService<ScheduledSensorReading>{
    SensorReadingAlert addSensorReadingAlert(long scheduledSensorReadingId, SensorReadingAlert sensorReadingAlert);
}
