package urbanjungletech.hardwareservice.event.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class HardwareEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(HardwareEventPublisher.class);
    private ApplicationEventPublisher applicationEventPublisher;

    public HardwareEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCreateHardwareEvent(long hardwareId){
        logger.debug("Publishing hardware create event.");
                HardwareCreateEvent hardwareCreateEvent = new HardwareCreateEvent(hardwareId);
                applicationEventPublisher.publishEvent(hardwareCreateEvent);
    }

    public void publishDeleteHardwareEvent(long hardwareId){
        HardwareDeleteEvent hardwareDeleteEvent = new HardwareDeleteEvent(hardwareId);
        this.applicationEventPublisher.publishEvent(hardwareDeleteEvent);
    }

    public void publishUpdateHardwareEvent(long hardwareId){
        HardwareUpdateEvent hardwareUpdateEvent = new HardwareUpdateEvent(hardwareId);
        this.applicationEventPublisher.publishEvent(hardwareUpdateEvent);
    }
}
