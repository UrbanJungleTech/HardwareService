package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.SensorReading;

public interface SensorReadingQueryService {
    SensorReading findSensorReadingBySensorId(Long sensorId);

    SensorReading findById(Long id);
}
