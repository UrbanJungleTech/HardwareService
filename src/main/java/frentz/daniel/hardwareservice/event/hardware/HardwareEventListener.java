package frentz.daniel.hardwareservice.event.hardware;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class HardwareEventListener {
    private final HardwareQueueService hardwareQueueService;
    private final HardwareService hardwareService;

    public HardwareEventListener(HardwareQueueService hardwareQueueService, HardwareService hardwareService){
        this.hardwareQueueService = hardwareQueueService;
        this.hardwareService = hardwareService;
    }

    @EventListener
    public void handleHardwareCreateEvent(HardwareUpdateStateEvent hardwareCreateEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareCreateEvent.getHardwareId());
        this.hardwareQueueService.registerHardware(hardware);
    }

    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.hardwareQueueService.deregisterHardware(hardware);
    }

    @EventListener
    public void handleHardwareUpdateStateEvent(HardwareUpdateStateEvent hardwareUpdateStateEvent){
        Hardware hardware = this.hardwareService.getHardware(hardwareUpdateStateEvent.getHardwareId());
        this.hardwareQueueService.sendStateToController(hardware);
    }
}
