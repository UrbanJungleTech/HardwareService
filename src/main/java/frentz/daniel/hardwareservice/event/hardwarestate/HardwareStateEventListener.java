package frentz.daniel.hardwareservice.event.hardwarestate;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class HardwareStateEventListener {

    private HardwareService hardwareService;
    private ControllerCommunicationService controllerCommunicationService;

    public HardwareStateEventListener(HardwareService hardwareService,
                                      ControllerCommunicationService controllerCommunicationService){
        this.hardwareService = hardwareService;
        this.controllerCommunicationService = controllerCommunicationService;
    }
    @Async
    @TransactionalEventListener
    public void handleHardwareUpdateStateEvent(HardwareStateUpdateEvent hardwareUpdateStateEvent){
        Hardware hardware = this.hardwareService.getHardwareByDesiredState(hardwareUpdateStateEvent.getHardwareStateId());
        this.controllerCommunicationService.sendStateToController(hardware);
    }
}
