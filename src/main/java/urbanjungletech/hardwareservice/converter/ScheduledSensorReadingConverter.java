package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingConverter {
    ScheduledSensorReading toModel(ScheduledSensorReadingEntity scheduledSensorReadingEntity);
    List<ScheduledSensorReading> toModels(List<ScheduledSensorReadingEntity> readings);
    void fillEntity(ScheduledSensorReadingEntity scheduledSensorReadingEntity, ScheduledSensorReading scheduledSensorReading);
}
