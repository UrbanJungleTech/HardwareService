package urbanjungletech.hardwareservice.schedule.sensor;

import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("SensorCronJobFactory")
public class ScheduledSensorReadingJobFactory extends SimpleJobFactory {

    private SensorQueryService sensorQueryService;
    private SensorReadingDAO sensorReadingDAO;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledSensorReadingJobFactory(SensorQueryService sensorQueryService,
                                            SensorReadingDAO sensorReadingDAO,
                                            ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.sensorQueryService = sensorQueryService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        long sensorId = bundle.getJobDetail().getJobDataMap().getLong("scheduledSensorReadingId");
        ScheduledSensorReadingJob result = new ScheduledSensorReadingJob(sensorId, this.sensorQueryService, this.sensorReadingDAO, this.scheduledSensorReadingQueryService);
        return result;
    }
}
