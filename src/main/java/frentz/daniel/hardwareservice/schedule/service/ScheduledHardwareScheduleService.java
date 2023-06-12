package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;

public interface ScheduledHardwareScheduleService {
    void start(ScheduledHardwareEntity cronJob);
    void deleteSchedule(long identifier);
}
