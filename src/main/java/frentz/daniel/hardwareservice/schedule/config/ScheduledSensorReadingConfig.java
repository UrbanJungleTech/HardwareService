package frentz.daniel.hardwareservice.schedule.config;

import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.schedule.sensor.SensorScheduleService;
import frentz.daniel.hardwareservice.service.ScheduledSensorReadingService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledSensorReadingConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ScheduledSensorReadingService scheduledSensorReadingService;
    private SensorScheduleService sensorScheduleService;

    public ScheduledSensorReadingConfig(ScheduledSensorReadingService scheduledSensorReadingService,
                                        SensorScheduleService sensorScheduleService){
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<ScheduledSensorReading> readings = this.scheduledSensorReadingService.getScheduledSensorReadings();
        readings.stream().forEach((ScheduledSensorReading reading) -> {
            this.sensorScheduleService.start(reading);
        });
    }
}