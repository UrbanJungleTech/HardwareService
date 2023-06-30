package frentz.daniel.hardwareservice.schedule.regulator;

import frentz.daniel.hardwareservice.exception.RegulatorJobAlreadyRunningException;
import frentz.daniel.hardwareservice.exception.SchedulerStartException;
import frentz.daniel.hardwareservice.model.Regulator;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RegulatorScheduleServiceImpl implements RegulatorScheduleService{

    private Scheduler scheduler;

    public RegulatorScheduleServiceImpl(@Qualifier("RegulatorScheduler") Scheduler scheduler){
        this.scheduler = scheduler;
    }

    @Override
    public void start(Regulator regulator) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("regulator", regulator);
            JobDetail details = JobBuilder.newJob(RegulatorJob.class)
                    .withIdentity(String.valueOf(regulator.getId()))
                    .usingJobData(jobDataMap)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(regulator.getCheckInterval()))
                    .withIdentity(String.valueOf(regulator.getId()))
                    .build();
            if(!scheduler.checkExists(details.getKey())) {
                this.scheduler.scheduleJob(details, trigger);
            }
            else{
                throw new RegulatorJobAlreadyRunningException(regulator.getId());
            }
        }
        catch(Exception ex){
            throw new SchedulerStartException(Regulator.class.getSimpleName(), regulator.getId());
        }
    }

    @Override
    public void stop(long regulatorId) {
        try {
            this.scheduler.deleteJob(new JobKey(String.valueOf(regulatorId)));
        }
        catch(Exception ex){
            throw new SchedulerStartException(Regulator.class.getSimpleName(), regulatorId);
        }
    }
}
