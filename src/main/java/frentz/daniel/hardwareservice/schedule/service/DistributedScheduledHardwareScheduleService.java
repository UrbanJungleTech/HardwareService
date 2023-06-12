package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "scheduling.distribution-strategy", havingValue = "distributed")
public class DistributedScheduledHardwareScheduleService implements ScheduledHardwareScheduleService {
    @Override
    public void start(ScheduledHardwareEntity cronJob) {
        //call scheduler microservice to start the job
    }

    @Override
    public void deleteSchedule(long identifier) {
        //call scheduler microservice to delete the job
    }
}
