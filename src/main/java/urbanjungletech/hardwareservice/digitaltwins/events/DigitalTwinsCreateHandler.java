package urbanjungletech.hardwareservice.digitaltwins.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.digitaltwins.service.DigitalTwinsService;
import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;
import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

//@Service
public class DigitalTwinsCreateHandler {
    private static Logger logger = LoggerFactory.getLogger(DigitalTwinsCreateHandler.class);
    private final DigitalTwinsService digitalTwinsDeleteService;

    public DigitalTwinsCreateHandler(DigitalTwinsService digitalTwinsDeleteService) {
        this.digitalTwinsDeleteService = digitalTwinsDeleteService;
    }
    @Async
    @TransactionalEventListener
    public void handleCreateEvent(CreateEvent createEvent){
        this.logger.debug("Received hardware create event, creating digital twin.");
        this.digitalTwinsDeleteService.createDigitalTwin(createEvent);
    }

    @Async
    @TransactionalEventListener
    public void handleDeleteEvent(DeleteEvent deleteEvent){
        this.logger.debug("Received hardware delete event, deleting digital twin.");
        this.digitalTwinsDeleteService.deleteDigitalTwin(deleteEvent);
    }
}
