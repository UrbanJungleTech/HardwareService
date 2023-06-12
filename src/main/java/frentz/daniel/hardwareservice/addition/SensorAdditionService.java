package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;

public interface SensorAdditionService extends AdditionService<Sensor>{
    ScheduledSensorReading addScheduledReading(long sensorId, ScheduledSensorReading scheduledSensorReading);
}
