package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.exception.ScheduledHardwareDeleteException;
import frentz.daniel.hardwareservice.exception.ScheduledHardwareStartException;
import frentz.daniel.hardwareservice.schedule.job.ScheduledHardwareJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "scheduling.distribution-strategy", havingValue = "local")
@Service
public class LocalScheduledHardwareScheduleService implements ScheduledHardwareScheduleService {

    private Scheduler scheduler;

    public LocalScheduledHardwareScheduleService(@Qualifier("HardwareScheduler") Scheduler scheduler){
        this.scheduler = scheduler;
    }

    @Override
    public void start(ScheduledHardwareEntity scheduledHardware) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("scheduledHardware", scheduledHardware);
            JobDetail details = JobBuilder.newJob(ScheduledHardwareJob.class)
                    .withIdentity(String.valueOf(scheduledHardware.getId()))
                    .usingJobData(jobDataMap)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduledHardware.getCronString()))
                    .withIdentity(String.valueOf(scheduledHardware.getId()))
                    .build();
            this.scheduler.scheduleJob(details, trigger);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledHardwareStartException(scheduledHardware.getId(), ex);
        }
    }


    @Override
    public void deleteSchedule(long identifier) {
        try {
            TriggerKey triggerKey = new TriggerKey(String.valueOf(identifier));
            this.scheduler.unscheduleJob(triggerKey);
        }
        catch(Exception ex){
            throw new ScheduledHardwareDeleteException(identifier, ex);
        }
    }
}
