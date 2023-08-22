package urbanjungletech.hardwareservice.schedule.sensor;

import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.service.ScheduledSensorReadingService;
import urbanjungletech.hardwareservice.service.SensorService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("SensorCronJobFactory")
public class ScheduledSensorReadingJobFactory extends SimpleJobFactory {

    private SensorService sensorService;
    private SensorReadingDAO sensorReadingDAO;
    private ScheduledSensorReadingService scheduledSensorReadingService;

    public ScheduledSensorReadingJobFactory(SensorService sensorService,
                                            SensorReadingDAO sensorReadingDAO,
                                            ScheduledSensorReadingService scheduledSensorReadingService){
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        long sensorId = bundle.getJobDetail().getJobDataMap().getLong("scheduledSensorReadingId");
        ScheduledSensorReadingJob result = new ScheduledSensorReadingJob(sensorId, this.sensorService, this.sensorReadingDAO, this.scheduledSensorReadingService);
        return result;
    }
}
