package urbanjungletech.hardwareservice.event.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

@Service
public class HardwareEventListener {
    private static final Logger logger = LoggerFactory.getLogger(HardwareEventListener.class);
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareQueryService hardwareQueryService;

    public HardwareEventListener(ControllerCommunicationService controllerCommunicationService, HardwareQueryService hardwareQueryService){
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareCreateEvent(HardwareCreateEvent HardwareCreateEvent){
        logger.debug("Sending hardware create event to hardware queue.");
        Hardware hardware = this.hardwareQueryService.getHardware(HardwareCreateEvent.getHardwareId());
        this.controllerCommunicationService.registerHardware(hardware);
    }

    @Async
    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareQueryService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.controllerCommunicationService.deregisterHardware(hardware);
    }

}
