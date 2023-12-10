package urbanjungletech.hardwareservice.schedule.sensor;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import urbanjungletech.hardwareservice.addition.SensorReadingAdditionService;
import urbanjungletech.hardwareservice.exception.exception.ScheduledSensorReadingJobException;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import urbanjungletech.hardwareservice.service.router.SensorReadingRouterService;

public class ScheduledSensorReadingJob implements Job {

    private long scheduledSensorReadingId;
    private SensorQueryService sensorQueryService;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private SensorReadingRouterService sensorReadingRouterService;
    private SensorReadingAdditionService sensorReadingAdditionService;

    public ScheduledSensorReadingJob(long scheduledSensorReadingId,
                                     SensorQueryService sensorQueryService,
                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                     SensorReadingRouterService sensorReadingRouterService,
                                     SensorReadingAdditionService sensorReadingAdditionService){
        this.scheduledSensorReadingId = scheduledSensorReadingId;
        this.sensorQueryService = sensorQueryService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
        this.sensorReadingRouterService = sensorReadingRouterService;
        this.sensorReadingAdditionService = sensorReadingAdditionService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            System.out.println("ScheduledSensorReadingJob.execute");
            ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingQueryService.getScheduledSensorReading(this.scheduledSensorReadingId);
            SensorReading result = this.sensorQueryService.readSensor(scheduledSensorReading.getSensorId());
            this.sensorReadingAdditionService.create(result);
            this.sensorReadingRouterService.route(scheduledSensorReading, result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledSensorReadingJobException(ex);
        }
    }
}
