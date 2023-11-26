package urbanjungletech.hardwareservice.event.sensorreading;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SensorReadingEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public SensorReadingEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishSensorReadingCreateEvent(Long sensorReadingId) {
        SensorReadingCreateEvent event = new SensorReadingCreateEvent(sensorReadingId);
        applicationEventPublisher.publishEvent(event);
    }
}
