package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingDAO {

    ScheduledSensorReadingEntity create(ScheduledSensorReading scheduledSensorReading);
    List<ScheduledSensorReadingEntity> getScheduledSensorReadings();
    ScheduledSensorReadingEntity getScheduledSensorReading(long scheduledSensorReadingId);
    void delete(long scheduledSensorReadingId);
}
