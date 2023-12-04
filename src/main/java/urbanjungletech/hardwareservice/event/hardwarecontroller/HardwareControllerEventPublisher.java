package urbanjungletech.hardwareservice.event.hardwarecontroller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class HardwareControllerEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public HardwareControllerEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishHardwareControllerCreateEvent(long hardwareControllerId){
        HardwareControllerCreateEvent hardwareControllerCreateEvent = new HardwareControllerCreateEvent(hardwareControllerId);
        this.applicationEventPublisher.publishEvent(hardwareControllerCreateEvent);
    }

    public void publishHardwareControllerDeleteEvent(long hardwareControllerId){
        HardwareControllerDeleteEvent hardwareControllerDeleteEvent = new HardwareControllerDeleteEvent(hardwareControllerId);
        this.applicationEventPublisher.publishEvent(hardwareControllerDeleteEvent);
    }

    public void publishHardwareControllerUpdateEvent(long hardwareControllerId){
        HardwareControllerUpdateEvent hardwareControllerUpdateEvent = new HardwareControllerUpdateEvent(hardwareControllerId);
        this.applicationEventPublisher.publishEvent(hardwareControllerUpdateEvent);
    }
}
