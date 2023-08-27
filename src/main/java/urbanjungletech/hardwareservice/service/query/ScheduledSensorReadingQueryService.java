package urbanjungletech.hardwareservice.service.query;


import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;

public interface ScheduledSensorReadingQueryService {
    ScheduledSensorReading getScheduledSensorReading(long id);
    List<ScheduledSensorReading> getScheduledSensorReadings();
}
