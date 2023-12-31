package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.RescheduleHardwareStartException;
import urbanjungletech.hardwareservice.exception.exception.ScheduledHardwareDeleteException;
import urbanjungletech.hardwareservice.exception.exception.ScheduledHardwareStartException;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

@Service
public class LocalScheduledHardwareScheduleService implements ScheduledHardwareScheduleService {

    private final Logger logger = LoggerFactory.getLogger(LocalScheduledHardwareScheduleService.class);
    private final Scheduler scheduler;
    private TimerQueryService timerQueryService;

    public LocalScheduledHardwareScheduleService(@Qualifier("HardwareScheduler") Scheduler scheduler,
                                                  TimerQueryService timerQueryService){
        this.scheduler = scheduler;
        this.timerQueryService = timerQueryService;
    }

    @Override
    public void start(long timerId) {
        try {
            logger.info("Starting scheduled hardware with id {} with cron string {}");
            Timer timer = this.timerQueryService.getTimer(timerId);
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("timerId", timerId);
            JobDetail details = JobBuilder.newJob(ScheduledHardwareJob.class)
                    .withIdentity(String.valueOf(timerId))
                    .usingJobData(jobDataMap)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(timer.getCronString()))
                    .withIdentity(String.valueOf(timerId))
                    .build();
            this.scheduler.scheduleJob(details, trigger);
        }
        catch(Exception ex){
            throw new ScheduledHardwareStartException(timerId, ex);
        }
    }

    @Override
    public void restartSchedule(long timerId){
        logger.info("Restarting scheduled hardware with id {}", timerId);
        try {
            Timer timer = this.timerQueryService.getTimer(timerId);
            this.scheduler.rescheduleJob(TriggerKey.triggerKey(String.valueOf(timerId)), TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(timer.getCronString()))
                    .withIdentity(String.valueOf(timerId))
                    .build());
        }
        catch(Exception ex){
            throw new RescheduleHardwareStartException(timerId, ex);
        }
    }


    @Override
    public void deleteSchedule(long scheduledHardwareId) {
        try {
            this.logger.info("Deleting scheduled hardware with id {}", scheduledHardwareId);
            TriggerKey triggerKey = new TriggerKey(String.valueOf(scheduledHardwareId));
            this.scheduler.unscheduleJob(triggerKey);
        }
        catch(Exception ex){
            logger.error("Error deleting scheduled hardware with id {} with exception", scheduledHardwareId, ex);
            throw new ScheduledHardwareDeleteException(scheduledHardwareId, ex);
        }
    }
}
