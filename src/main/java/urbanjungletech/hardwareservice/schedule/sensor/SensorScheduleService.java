package urbanjungletech.hardwareservice.schedule.sensor;


import org.quartz.SchedulerException;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

public interface SensorScheduleService {
    void start(ScheduledSensorReading scheduledSensorReading);
    void delete(long identifier);
    void pause(long identifier);

    void deleteAll() throws SchedulerException;
}
