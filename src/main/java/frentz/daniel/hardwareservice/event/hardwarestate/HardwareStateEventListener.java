package frentz.daniel.hardwareservice.event.hardwarestate;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class HardwareStateEventListener {

    private HardwareService hardwareService;
    private HardwareQueueService hardwareQueueService;

    public HardwareStateEventListener(HardwareService hardwareService,
                                      HardwareQueueService hardwareQueueService){
        this.hardwareService = hardwareService;
        this.hardwareQueueService = hardwareQueueService;
    }
    @Async
    @TransactionalEventListener
    public void handleHardwareUpdateStateEvent(HardwareStateUpdateEvent hardwareUpdateStateEvent){
        Hardware hardware = this.hardwareService.getHardwareByDesiredState(hardwareUpdateStateEvent.getHardwareStateId());
        System.out.println("got hardware with state " + hardware.getDesiredState().getState());
        this.hardwareQueueService.sendStateToController(hardware);
    }
}
