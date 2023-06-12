package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.client.model.SensorReading;

public interface SensorService {
    SensorReading readSensor(long sensorId);
    double readAverageSensor(long controllerSerialNumber, String sensorType);
    ScheduledSensorReading createScheduledReading(ScheduledSensorReading scheduledSensorReading);
    Sensor getSensor(String serialNumber, int hardwarePort);

    Sensor getSensor(long sensorId);
}