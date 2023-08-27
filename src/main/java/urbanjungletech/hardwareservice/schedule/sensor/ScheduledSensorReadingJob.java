package urbanjungletech.hardwareservice.schedule.sensor;

import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.exception.exception.ScheduledSensorReadingJobException;
import urbanjungletech.hardwareservice.model.AlertType;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import urbanjungletech.hardwareservice.model.SensorReading;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ScheduledSensorReadingJob implements Job {

    private long scheduledSensorReadingId;
    private SensorQueryService sensorQueryService;
    private SensorReadingDAO sensorReadingDAO;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledSensorReadingJob(long scheduledSensorReadingId,
                                     SensorQueryService sensorQueryService,
                                     SensorReadingDAO sensorReadingDAO,
                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.scheduledSensorReadingId = scheduledSensorReadingId;
        this.sensorQueryService = sensorQueryService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingQueryService.getScheduledSensorReading(this.scheduledSensorReadingId);
            SensorReading result = this.sensorQueryService.readSensor(scheduledSensorReading.getSensorId());
            for (SensorReadingAlert sensorReadingAlert : scheduledSensorReading.getSensorReadingAlerts()) {
                if (sensorReadingAlert.getAlertType() == AlertType.MIN && result.getReading() < sensorReadingAlert.getThreshold() ||
                        sensorReadingAlert.getAlertType() == AlertType.MAX && result.getReading() > sensorReadingAlert.getThreshold()) {
                    //trigger the observable here
                }
            }
            this.sensorReadingDAO.createAndSave(result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledSensorReadingJobException(ex);
        }
    }
}
