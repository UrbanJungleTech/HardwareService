package frentz.daniel.hardwareservice.schedule.service;

import frentz.daniel.hardwareservice.model.Regulator;
import frentz.daniel.hardwareservice.schedule.job.RegulatorJob;
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
            this.scheduler.scheduleJob(details, trigger);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
