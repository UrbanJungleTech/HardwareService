package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;

public interface SensorQueryService {
    SensorReading readSensor(long sensorId);
    Sensor getSensor(String serialNumber, String hardwarePort);

    Sensor getSensor(long sensorId);
}
