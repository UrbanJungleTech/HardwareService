package urbanjungletech.hardwareservice.schedule.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareJobFactory;
import urbanjungletech.hardwareservice.schedule.sensor.ScheduledSensorReadingJobFactory;

import java.util.Properties;

@Configuration
public class SchedulerConfig {

    Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    @Bean("HardwareScheduler")
    Scheduler getHardwareScheduler(ScheduledHardwareJobFactory jobFactory){
        try {
            Properties config = new Properties();
            config.setProperty("org.quartz.scheduler.instanceName", "HardwareScheduler");
            config.setProperty("org.quartz.threadPool.threadCount", "20");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory(config);
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);
            scheduler.start();
            return scheduler;
        }
        catch(Exception ex){
            logger.error("Failed to start scheduler.");
            ex.printStackTrace();
        }
        return null;
    }

    @Bean("SensorScheduler")
    public Scheduler getSensorScheduler(ScheduledSensorReadingJobFactory jobFactory){
        try {
            Properties config = new Properties();
            config.setProperty("org.quartz.scheduler.instanceName", "SensorScheduler");
            config.setProperty("org.quartz.threadPool.threadCount", "20");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory(config);
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(jobFactory);
            scheduler.start();
            return scheduler;
        }
        catch(Exception ex){
            logger.error("Failed to start scheduler.");
            ex.printStackTrace();
        }
        return null;
    }

}
