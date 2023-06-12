package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import frentz.daniel.hardwareservice.client.model.SensorReading;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorReadingDAO {
    SensorReadingEntity createAndSave(SensorReading sensorReading);
    List<SensorReading> getReadings(long sensorId, LocalDateTime startDate, LocalDateTime endDate);
}
