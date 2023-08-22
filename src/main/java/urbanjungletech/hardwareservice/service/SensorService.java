package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;

public interface SensorService {
    SensorReading readSensor(long sensorId);
    double readAverageSensor(long controllerSerialNumber, String sensorType);
    ScheduledSensorReading createScheduledReading(ScheduledSensorReading scheduledSensorReading);
    Sensor getSensor(String serialNumber, String hardwarePort);

    Sensor getSensor(long sensorId);
}
