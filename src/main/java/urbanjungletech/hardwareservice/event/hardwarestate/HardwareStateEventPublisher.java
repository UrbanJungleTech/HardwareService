package urbanjungletech.hardwareservice.event.hardwarestate;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public HardwareStateEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishHardwareStateUpdateEvent(long hardwareStateId){
        HardwareStateUpdateEvent hardwareStateUpdateEvent = new HardwareStateUpdateEvent(hardwareStateId);
        this.applicationEventPublisher.publishEvent(hardwareStateUpdateEvent);
    }

    public void publishHardwareStateCreateEvent(long hardwareStateId){
        HardwareStateCreateEvent hardwareStateCreateEvent = new HardwareStateCreateEvent(hardwareStateId);
        this.applicationEventPublisher.publishEvent(hardwareStateCreateEvent);
    }

    public void publishHardwareStateDeleteEvent(long hardwareStateId){
        HardwareStateDeleteEvent hardwareStateDeleteEvent = new HardwareStateDeleteEvent(hardwareStateId);
        this.applicationEventPublisher.publishEvent(hardwareStateDeleteEvent);
    }

}
