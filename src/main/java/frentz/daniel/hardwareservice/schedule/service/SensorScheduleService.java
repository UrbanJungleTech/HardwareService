package frentz.daniel.hardwareservice.schedule.service;


import frentz.daniel.hardwareservice.model.ScheduledSensorReading;

public interface SensorScheduleService {
    void start(ScheduledSensorReading scheduledSensorReading);
    void delete(long identifier);
    void pause(long identifier);
}
