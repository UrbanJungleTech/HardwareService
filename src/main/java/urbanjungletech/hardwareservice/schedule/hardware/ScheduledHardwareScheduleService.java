package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.SchedulerException;

public interface ScheduledHardwareScheduleService {
    void start(long scheduledHardwareId);

    void restartSchedule(long scheduledHardwareId);

    void deleteSchedule(long scheduledHardwareId);

    void deleteAllSchedules() throws SchedulerException;
}
