package urbanjungletech.hardwareservice.event.sensor;

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

    public void publishSensorUpdateEvent(long sensorId) {
        SensorUpdateEvent event = new SensorUpdateEvent(sensorId);
        applicationEventPublisher.publishEvent(event);
    }
}
