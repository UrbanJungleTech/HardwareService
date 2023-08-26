package urbanjungletech.hardwareservice.event.scheduledreading;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.schedule.sensor.SensorScheduleService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ScheduledReadingEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledReadingEventListener.class);
    private SensorScheduleService sensorScheduleService;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledReadingEventListener(SensorScheduleService sensorScheduleService,
                                         ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
    }

    @Async
    @TransactionalEventListener
    public void handleScheduledReadingCreateEvent(ScheduledReadingCreateEvent scheduledReadingCreateEvent){
        LOGGER.debug("Scheduling scheduled sensor readings");
        ScheduledSensorReading reading = this.scheduledSensorReadingQueryService.getScheduledSensorReading(scheduledReadingCreateEvent.getScheduledReadingId());
        this.sensorScheduleService.start(reading);
    }
}
