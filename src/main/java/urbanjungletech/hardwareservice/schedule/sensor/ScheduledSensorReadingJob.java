package urbanjungletech.hardwareservice.schedule.sensor;

import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.exception.ScheduledSensorReadingJobException;
import urbanjungletech.hardwareservice.model.AlertType;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.ScheduledSensorReadingService;
import urbanjungletech.hardwareservice.service.SensorService;
import urbanjungletech.hardwareservice.model.SensorReading;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ScheduledSensorReadingJob implements Job {

    private long scheduledSensorReadingId;
    private SensorService sensorService;
    private SensorReadingDAO sensorReadingDAO;
    private ScheduledSensorReadingService scheduledSensorReadingService;

    public ScheduledSensorReadingJob(long scheduledSensorReadingId,
                                     SensorService sensorService,
                                     SensorReadingDAO sensorReadingDAO,
                                     ScheduledSensorReadingService scheduledSensorReadingService){
        this.scheduledSensorReadingId = scheduledSensorReadingId;
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingService.getScheduledSensorReading(this.scheduledSensorReadingId);
            SensorReading result = this.sensorService.readSensor(scheduledSensorReading.getSensorId());
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
