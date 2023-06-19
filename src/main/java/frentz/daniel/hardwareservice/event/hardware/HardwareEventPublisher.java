package frentz.daniel.hardwareservice.event.hardware;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class HardwareEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public HardwareEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    public void publishCreateHardwareEvent(long hardwareId){
        HardwareCreateEvent hardwareCreateEvent = new HardwareCreateEvent(hardwareId);
        this.applicationEventPublisher.publishEvent(hardwareCreateEvent);
    }

    public void publishDeleteHardwareEvent(long hardwareId){
        HardwareDeleteEvent hardwareDeleteEvent = new HardwareDeleteEvent(hardwareId);
        this.applicationEventPublisher.publishEvent(hardwareDeleteEvent);
    }

    public void publishUpdateHardwareStateEvent(long hardwareId){
        HardwareUpdateStateEvent hardwareUpdateStateEvent = new HardwareUpdateStateEvent(hardwareId);
        this.applicationEventPublisher.publishEvent(hardwareUpdateStateEvent);
    }
}
