package frentz.daniel.hardwareservice.event.scheduledreading;

import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.schedule.service.SensorScheduleService;
import frentz.daniel.hardwareservice.service.ScheduledSensorReadingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ScheduledReadingEventListener {

    private SensorScheduleService sensorScheduleService;
    private ScheduledSensorReadingService scheduledSensorReadingService;

    public ScheduledReadingEventListener(SensorScheduleService sensorScheduleService,
                                         ScheduledSensorReadingService scheduledSensorReadingService){
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }

    @TransactionalEventListener
    public void handleScheduledReadingCreateEvent(ScheduledReadingCreateEvent scheduledReadingCreateEvent){
        ScheduledSensorReading reading = this.scheduledSensorReadingService.getScheduledSensorReading(scheduledReadingCreateEvent.getScheduledReadingId());
        this.sensorScheduleService.start(reading);
    }
}
