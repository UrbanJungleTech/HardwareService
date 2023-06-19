package frentz.daniel.hardwareservice.event.sensor;

import frentz.daniel.hardwareservice.service.HardwareQueueService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SensorEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public SensorEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishSensorDeleteEvent(long sensorId) {
        SensorDeleteEvent event = new SensorDeleteEvent(sensorId);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishSensorCreateEvent(long sensorId) {
        SensorCreateEvent event = new SensorCreateEvent(sensorId);
        applicationEventPublisher.publishEvent(event);
    }
}
