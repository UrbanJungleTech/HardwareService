package urbanjungletech.hardwareservice.schedule.sensor;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;

@Service("SensorCronJobFactory")
public class ScheduledSensorReadingJobFactory extends SimpleJobFactory {

    private final SensorQueryService sensorQueryService;
    private final SensorReadingDAO sensorReadingDAO;
    private final ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private final ActionExecutionService actionExecutionService;

    public ScheduledSensorReadingJobFactory(SensorQueryService sensorQueryService,
                                            SensorReadingDAO sensorReadingDAO,
                                            ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                            ActionExecutionService actionExecutionService){
        this.sensorQueryService = sensorQueryService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
        this.actionExecutionService = actionExecutionService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        long sensorId = bundle.getJobDetail().getJobDataMap().getLong("scheduledSensorReadingId");
        ScheduledSensorReadingJob result = new ScheduledSensorReadingJob(sensorId,
                this.sensorQueryService,
                this.sensorReadingDAO,
                this.scheduledSensorReadingQueryService,
                this.actionExecutionService);
        return result;
    }
}
