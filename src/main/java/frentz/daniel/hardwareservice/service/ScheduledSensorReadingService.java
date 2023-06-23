package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingService {
    ScheduledSensorReading getScheduledSensorReading(long id);
    List<ScheduledSensorReading> getScheduledSensorReadings();
}
