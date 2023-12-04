package urbanjungletech.hardwareservice.service.scheduledsensorreading;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;

public interface SensorReadingRouterService {
    void route(ScheduledSensorReading scheduledSensorReading, SensorReading sensorReading);
}
