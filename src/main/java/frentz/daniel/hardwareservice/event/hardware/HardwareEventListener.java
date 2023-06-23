package frentz.daniel.hardwareservice.event.hardware;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
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
    private final HardwareQueueService hardwareQueueService;
    private final HardwareService hardwareService;

    public HardwareEventListener(HardwareQueueService hardwareQueueService, HardwareService hardwareService){
        this.hardwareQueueService = hardwareQueueService;
        this.hardwareService = hardwareService;
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareCreateEvent(HardwareCreateEvent hardwareCreateEvent){
        logger.debug("Sending hardware create event to hardware queue.");
        Hardware hardware = this.hardwareService.getHardware(hardwareCreateEvent.getHardwareId());
        this.hardwareQueueService.registerHardware(hardware);
    }

    @Async
    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.hardwareQueueService.deregisterHardware(hardware);
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareUpdateStateEvent(HardwareUpdateStateEvent hardwareUpdateStateEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareUpdateStateEvent.getHardwareId());
        this.hardwareQueueService.sendStateToController(hardware);
    }
}
