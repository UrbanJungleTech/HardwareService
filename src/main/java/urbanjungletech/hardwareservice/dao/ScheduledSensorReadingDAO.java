package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingDAO {

    ScheduledSensorReadingEntity create(ScheduledSensorReading scheduledSensorReading);
    List<ScheduledSensorReadingEntity> getScheduledSensorReadings();
    ScheduledSensorReadingEntity getScheduledSensorReading(long scheduledSensorReadingId);
    void delete(long scheduledSensorReadingId);
    ScheduledSensorReadingEntity update(ScheduledSensorReading scheduledSensorReading);
}
