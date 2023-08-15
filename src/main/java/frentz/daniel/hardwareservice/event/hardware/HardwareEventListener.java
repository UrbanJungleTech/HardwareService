package frentz.daniel.hardwareservice.event.hardware;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class HardwareEventListener {
    private static final Logger logger = LoggerFactory.getLogger(HardwareEventListener.class);
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareService hardwareService;

    public HardwareEventListener(ControllerCommunicationService controllerCommunicationService, HardwareService hardwareService){
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareService = hardwareService;
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareCreateEvent(HardwareCreateEvent HardwareCreateEvent){
        logger.debug("Sending hardware create event to hardware queue.");
        Hardware hardware = this.hardwareService.getHardware(HardwareCreateEvent.getHardwareId());
        this.controllerCommunicationService.registerHardware(hardware);
    }

    @Async
    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.controllerCommunicationService.deregisterHardware(hardware);
    }

}
