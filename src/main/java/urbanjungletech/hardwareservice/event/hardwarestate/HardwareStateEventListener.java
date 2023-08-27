package urbanjungletech.hardwareservice.event.hardwarestate;

import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class HardwareStateEventListener {

    private HardwareQueryService hardwareQueryService;
    private ControllerCommunicationService controllerCommunicationService;

    public HardwareStateEventListener(HardwareQueryService hardwareQueryService,
                                      ControllerCommunicationService controllerCommunicationService){
        this.hardwareQueryService = hardwareQueryService;
        this.controllerCommunicationService = controllerCommunicationService;
    }
    @Async
    @TransactionalEventListener
    public void handleHardwareUpdateStateEvent(HardwareStateUpdateEvent hardwareUpdateStateEvent){
        Hardware hardware = this.hardwareQueryService.getHardwareByDesiredState(hardwareUpdateStateEvent.getHardwareStateId());
        this.controllerCommunicationService.sendStateToController(hardware);
    }
}
