package urbanjungletech.hardwareservice.schedule.config;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.schedule.sensor.SensorScheduleService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledSensorReadingConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;
    private SensorScheduleService sensorScheduleService;

    public ScheduledSensorReadingConfig(ScheduledSensorReadingQueryService scheduledSensorReadingQueryService,
                                        SensorScheduleService sensorScheduleService){
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<ScheduledSensorReading> readings = this.scheduledSensorReadingQueryService.getScheduledSensorReadings();
        readings.stream().forEach((ScheduledSensorReading reading) -> {
            this.sensorScheduleService.start(reading);
        });
    }
}
