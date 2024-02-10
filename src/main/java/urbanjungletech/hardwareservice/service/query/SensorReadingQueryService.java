package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.SensorReading;

import java.util.List;

public interface SensorReadingQueryService {
    SensorReading findSensorReadingBySensorId(Long sensorId);

    SensorReading findById(Long id);
}
