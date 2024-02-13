package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

public interface SensorQueryService {
    SensorReading readSensor(long sensorId);
    Sensor getSensor(String serialNumber, String hardwarePort);

    Sensor getSensor(long sensorId);
}
