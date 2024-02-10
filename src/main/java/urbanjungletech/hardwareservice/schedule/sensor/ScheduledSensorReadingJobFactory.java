package urbanjungletech.hardwareservice.schedule.sensor;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.SensorReadingAdditionService;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;
import urbanjungletech.hardwareservice.service.router.SensorReadingRouterService;

@Service("SensorCronJobFactory")
public class ScheduledSensorReadingJobFactory extends SimpleJobFactory {

    private final SensorQueryService sensorQueryService;
    private final ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private final SensorReadingRouterService sensorReadingRouterService;
    private final SensorReadingAdditionService sensorReadingAdditionService;
    private final SensorReadingRouterQueryService sensorReadingRouterQueryService;

    public ScheduledSensorReadingJobFactory(SensorQueryService sensorQueryService,
                                            ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                            SensorReadingRouterService sensorReadingRouterService,
                                            SensorReadingAdditionService sensorReadingAdditionService,
                                            SensorReadingRouterQueryService sensorReadingRouterQueryService){
        this.sensorQueryService = sensorQueryService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
        this.sensorReadingRouterService = sensorReadingRouterService;
        this.sensorReadingAdditionService = sensorReadingAdditionService;
        this.sensorReadingRouterQueryService = sensorReadingRouterQueryService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        long sensorId = bundle.getJobDetail().getJobDataMap().getLong("scheduledSensorReadingId");
        ScheduledSensorReadingJob result = new ScheduledSensorReadingJob(sensorId,
                this.sensorQueryService,
                this.scheduledSensorReadingQueryService,
                this.sensorReadingRouterService,
                this.sensorReadingAdditionService,
                this.sensorReadingRouterQueryService);
        return result;
    }
}
