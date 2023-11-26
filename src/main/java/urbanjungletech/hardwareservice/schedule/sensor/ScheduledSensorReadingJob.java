package urbanjungletech.hardwareservice.schedule.sensor;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.exception.exception.ScheduledSensorReadingJobException;
import urbanjungletech.hardwareservice.model.*;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;

public class ScheduledSensorReadingJob implements Job {

    private long scheduledSensorReadingId;
    private SensorQueryService sensorQueryService;
    private SensorReadingDAO sensorReadingDAO;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private ActionExecutionService actionExecutionService;

    public ScheduledSensorReadingJob(long scheduledSensorReadingId,
                                     SensorQueryService sensorQueryService,
                                     SensorReadingDAO sensorReadingDAO,
                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                     ActionExecutionService actionExecutionService){
        this.scheduledSensorReadingId = scheduledSensorReadingId;
        this.sensorQueryService = sensorQueryService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
        this.actionExecutionService = actionExecutionService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingQueryService.getScheduledSensorReading(this.scheduledSensorReadingId);
            SensorReading result = this.sensorQueryService.readSensor(scheduledSensorReading.getSensorId());
            this.sensorReadingDAO.createAndSave(result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledSensorReadingJobException(ex);
        }
    }
}
