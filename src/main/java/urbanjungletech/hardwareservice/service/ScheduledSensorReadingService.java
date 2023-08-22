package urbanjungletech.hardwareservice.service;


import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingService {
    ScheduledSensorReading getScheduledSensorReading(long id);
    List<ScheduledSensorReading> getScheduledSensorReadings();
}
