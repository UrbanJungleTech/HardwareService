package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorReadingDAO {
    SensorReadingEntity createAndSave(SensorReading sensorReading);
    List<SensorReading> getReadings(long sensorId, LocalDateTime startDate, LocalDateTime endDate);
}
