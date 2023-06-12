package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;

public interface SensorScheduleService {
    void start(ScheduledSensorReadingEntity scheduledSensorReading);
    void delete(long identifier);
    void pause(long identifier);
}
