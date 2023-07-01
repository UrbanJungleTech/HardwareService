package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.model.Regulator;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.model.Sensor;

public interface SensorAdditionService extends AdditionService<Sensor>{
    ScheduledSensorReading addScheduledReading(long sensorId, ScheduledSensorReading scheduledSensorReading);
}
