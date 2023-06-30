package frentz.daniel.hardwareservice.event.scheduledreading;

import frentz.daniel.hardwareservice.config.LoggingMqttMessageInterceptor;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.schedule.sensor.SensorScheduleService;
import frentz.daniel.hardwareservice.service.ScheduledSensorReadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ScheduledReadingEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledReadingEventListener.class);
    private SensorScheduleService sensorScheduleService;
    private ScheduledSensorReadingService scheduledSensorReadingService;

    public ScheduledReadingEventListener(SensorScheduleService sensorScheduleService,
                                         ScheduledSensorReadingService scheduledSensorReadingService){
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }

    @Async
    @TransactionalEventListener
    public void handleScheduledReadingCreateEvent(ScheduledReadingCreateEvent scheduledReadingCreateEvent){
        LOGGER.debug("Scheduling scheduled sensor readings");
        ScheduledSensorReading reading = this.scheduledSensorReadingService.getScheduledSensorReading(scheduledReadingCreateEvent.getScheduledReadingId());
        this.sensorScheduleService.start(reading);
    }
}
