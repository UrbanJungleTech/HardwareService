package frentz.daniel.hardwareservice.schedule.sensor;

import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.repository.ScheduledSensorReadingRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SensorScheduleReadingServiceImpl implements SensorScheduleService{

    private Scheduler scheduler;
    private ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    private ScheduledSensorReadingConverter scheduledSensorReadingConverter;

    public SensorScheduleReadingServiceImpl(@Qualifier("SensorScheduler") Scheduler scheduler,
                                            ScheduledSensorReadingRepository scheduledSensorReadingRepository,
                                            ScheduledSensorReadingConverter scheduledSensorReadingConverter){
        this.scheduler = scheduler;
        this.scheduledSensorReadingRepository = scheduledSensorReadingRepository;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
    }

    @Override
    public void start(ScheduledSensorReading scheduledSensorReading) {
        try {
            JobDetail details = JobBuilder.newJob(ScheduledSensorReadingJob.class)
                    .withIdentity(String.valueOf(scheduledSensorReading.getId()))
                    .usingJobData("sensorId", scheduledSensorReading.getSensorId())
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduledSensorReading.getCronString()))
                    .withIdentity(String.valueOf(scheduledSensorReading.getId()))
                    .build();
            this.scheduler.scheduleJob(details, trigger);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long identifier) {
        try {
            TriggerKey triggerKey = new TriggerKey(String.valueOf(identifier));
            this.scheduler.unscheduleJob(triggerKey);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void pause(long identifier) {

    }


}
