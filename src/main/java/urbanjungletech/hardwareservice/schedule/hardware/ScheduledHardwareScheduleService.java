package urbanjungletech.hardwareservice.schedule.hardware;

public interface ScheduledHardwareScheduleService {
    void start(long scheduledHardwareId);

    void restartSchedule(long scheduledHardwareId);

    void deleteSchedule(long scheduledHardwareId);
}
