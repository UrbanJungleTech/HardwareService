package frentz.daniel.hardwareservice.schedule.job;

import frentz.daniel.hardwareservice.dao.SensorReadingDAO;
import frentz.daniel.hardwareservice.exception.ScheduledSensorReadingJobException;
import frentz.daniel.hardwareservice.service.SensorService;
import frentz.daniel.hardwareservice.model.SensorReading;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ScheduledSensorReadingJob implements Job {

    private long sensorId;
    private SensorService sensorService;
    private SensorReadingDAO sensorReadingDAO;

    public ScheduledSensorReadingJob(long sensorId,
                                     SensorService sensorService,
                                     SensorReadingDAO sensorReadingDAO){
        this.sensorId = sensorId;
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            SensorReading result = this.sensorService.readSensor(this.sensorId);
            this.sensorReadingDAO.createAndSave(result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ScheduledSensorReadingJobException(ex);
        }
    }
}
