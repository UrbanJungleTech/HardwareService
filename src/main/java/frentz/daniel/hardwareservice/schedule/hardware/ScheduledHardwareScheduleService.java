package frentz.daniel.hardwareservice.schedule.hardware;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import org.quartz.SchedulerException;

public interface ScheduledHardwareScheduleService {
    void start(long scheduledHardwareId);

    void restartSchedule(long scheduledHardwareId);

    void deleteSchedule(long scheduledHardwareId);

    void deleteAllSchedules() throws SchedulerException;
}
