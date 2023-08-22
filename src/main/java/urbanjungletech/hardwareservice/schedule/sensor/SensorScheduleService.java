package urbanjungletech.hardwareservice.schedule.sensor;


import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import org.quartz.SchedulerException;

public interface SensorScheduleService {
    void start(ScheduledSensorReading scheduledSensorReading);
    void delete(long identifier);
    void pause(long identifier);

    void deleteAll() throws SchedulerException;
}
