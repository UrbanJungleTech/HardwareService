package urbanjungletech.hardwareservice.schedule.sensor;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import urbanjungletech.hardwareservice.addition.SensorReadingAdditionService;
import urbanjungletech.hardwareservice.exception.exception.ScheduledSensorReadingJobException;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;
import urbanjungletech.hardwareservice.service.router.SensorReadingRouterService;

public class ScheduledSensorReadingJob implements Job {

    private long scheduledSensorReadingId;
    private SensorQueryService sensorQueryService;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private SensorReadingRouterService sensorReadingRouterService;
    private SensorReadingAdditionService sensorReadingAdditionService;
    private SensorReadingRouterQueryService sensorReadingRouterQueryService;

    public ScheduledSensorReadingJob(long scheduledSensorReadingId,
                                     SensorQueryService sensorQueryService,
                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                     SensorReadingRouterService sensorReadingRouterService,
                                     SensorReadingAdditionService sensorReadingAdditionService,
                                     SensorReadingRouterQueryService sensorReadingRouterQueryService){
        this.scheduledSensorReadingId = scheduledSensorReadingId;
        this.sensorQueryService = sensorQueryService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
        this.sensorReadingRouterService = sensorReadingRouterService;
        this.sensorReadingAdditionService = sensorReadingAdditionService;
        this.sensorReadingRouterQueryService = sensorReadingRouterQueryService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingQueryService.getScheduledSensorReading(this.scheduledSensorReadingId);
            SensorReading result = this.sensorQueryService.readSensor(scheduledSensorReading.getSensorId());
            this.sensorReadingAdditionService.create(result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledSensorReadingJobException(ex);
        }
    }
}
