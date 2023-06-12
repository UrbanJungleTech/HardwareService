package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingDAO {

    ScheduledSensorReadingEntity create(ScheduledSensorReading scheduledSensorReading);
    List<ScheduledSensorReadingEntity> getScheduledSensorReadings();
    void delete(long scheduledSensorReadingId);
}
