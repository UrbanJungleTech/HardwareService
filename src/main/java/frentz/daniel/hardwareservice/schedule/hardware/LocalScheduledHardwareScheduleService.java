package frentz.daniel.hardwareservice.schedule.hardware;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.exception.ScheduledHardwareDeleteException;
import frentz.daniel.hardwareservice.exception.ScheduledHardwareStartException;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.service.ScheduledHardwareService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "scheduling.distribution-strategy", havingValue = "local")
@Service
public class LocalScheduledHardwareScheduleService implements ScheduledHardwareScheduleService {

    private Scheduler scheduler;
    private ScheduledHardwareService scheduledHardwareService;

    public LocalScheduledHardwareScheduleService(@Qualifier("HardwareScheduler") Scheduler scheduler,
                                                  ScheduledHardwareService scheduledHardwareService){
        this.scheduler = scheduler;
        this.scheduledHardwareService = scheduledHardwareService;
    }

    @Override
    public void start(long scheduledHardwareId) {
        try {
            ScheduledHardware scheduledHardware = this.scheduledHardwareService.getById(scheduledHardwareId);
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
            throw new ScheduledHardwareStartException(scheduledHardwareId, ex);
        }
    }

    @Override
    public void restartSchedule(long timerId){
        this.deleteSchedule(timerId);
        this.start(timerId);
    }


    @Override
    public void deleteSchedule(long scheduledHardwareId) {
        try {
            TriggerKey triggerKey = new TriggerKey(String.valueOf(scheduledHardwareId));
            this.scheduler.unscheduleJob(triggerKey);
        }
        catch(Exception ex){
            throw new ScheduledHardwareDeleteException(scheduledHardwareId, ex);
        }
    }
}
