package urbanjungletech.hardwareservice.schedule.hardware;

import urbanjungletech.hardwareservice.exception.exception.ScheduledHardwareDeleteException;
import urbanjungletech.hardwareservice.exception.exception.ScheduledHardwareStartException;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "scheduling.distribution-strategy", havingValue = "local")
@Service
public class LocalScheduledHardwareScheduleService implements ScheduledHardwareScheduleService {

    Logger logger = LoggerFactory.getLogger(LocalScheduledHardwareScheduleService.class);
    private Scheduler scheduler;
    private ScheduledHardwareQueryService scheduledHardwareQueryService;

    public LocalScheduledHardwareScheduleService(@Qualifier("HardwareScheduler") Scheduler scheduler,
                                                  ScheduledHardwareQueryService scheduledHardwareQueryService){
        this.scheduler = scheduler;
        this.scheduledHardwareQueryService = scheduledHardwareQueryService;
    }

    @Override
    public void start(long scheduledHardwareId) {
        try {
            logger.info("Starting scheduled hardware with id {}", scheduledHardwareId);
            ScheduledHardware scheduledHardware = this.scheduledHardwareQueryService.getById(scheduledHardwareId);
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

    @Override
    public void deleteAllSchedules() throws SchedulerException {
        this.scheduler.clear();
    }
}
