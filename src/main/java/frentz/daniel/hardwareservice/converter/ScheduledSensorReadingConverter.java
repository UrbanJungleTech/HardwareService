package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingConverter {
    ScheduledSensorReading toModel(ScheduledSensorReadingEntity scheduledSensorReadingEntity);
    List<ScheduledSensorReading> toModels(List<ScheduledSensorReadingEntity> readings);
}
