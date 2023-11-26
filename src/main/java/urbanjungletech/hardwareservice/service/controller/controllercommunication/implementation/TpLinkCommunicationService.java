package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;
import urbanjungletech.hardwareservice.service.tplink.TplinkActionService;

@Service()
@HardwareControllerCommunicationService(type="tplink")
public class TpLinkCommunicationService extends ControllerCommunicationServiceImplementation {

    private final TplinkActionService tplinkActionService;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    public TpLinkCommunicationService(TplinkActionService tplinkActionService,
                                      HardwareControllerQueryService hardwareControllerQueryService) {
        this.tplinkActionService = tplinkActionService;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }
    @Override
    public void sendStateToController(Hardware hardware) {
        this.tplinkActionService.setState(hardware);
    }

    @Override
    public void registerHardware(Hardware hardware) {
        this.tplinkActionService.setState(hardware);
    }
}
