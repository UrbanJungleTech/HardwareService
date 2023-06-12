package frentz.daniel.hardwareservice.schedule.factory;

import frentz.daniel.hardwareservice.schedule.job.ScheduledSensorReadingJob;
import frentz.daniel.hardwareservice.dao.SensorReadingDAO;
import frentz.daniel.hardwareservice.service.SensorService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("SensorCronJobFactory")
public class ScheduledSensorReadingJobFactory extends SimpleJobFactory {

    private SensorService sensorService;
    private SensorReadingDAO sensorReadingDAO;

    public ScheduledSensorReadingJobFactory(SensorService sensorService, SensorReadingDAO sensorReadingDAO){
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        long sensorId = bundle.getJobDetail().getJobDataMap().getLong("sensorId");
        ScheduledSensorReadingJob result = new ScheduledSensorReadingJob(sensorId, this.sensorService, this.sensorReadingDAO);
        return result;
    }
}
