package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

public interface SensorAdditionService extends AdditionService<Sensor>{
    ScheduledSensorReading addScheduledReading(long sensorId, ScheduledSensorReading scheduledSensorReading);
}
