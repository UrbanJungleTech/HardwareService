package frentz.daniel.hardwareservice.config.schedulers;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.schedule.service.SensorScheduleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledSensorReadingConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private SensorScheduleService sensorScheduleService;

    public ScheduledSensorReadingConfig(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                        SensorScheduleService sensorScheduleService){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorScheduleService = sensorScheduleService;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<ScheduledSensorReadingEntity> readings = this.scheduledSensorReadingDAO.getScheduledSensorReadings();
        readings.stream().forEach((ScheduledSensorReadingEntity reading) -> {
            this.sensorScheduleService.start(reading);
        });
    }
}
