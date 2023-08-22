package urbanjungletech.hardwareservice.jsonrpc.method;

import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.HardwareControllerService;
import urbanjungletech.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetInitialState implements RpcMethod{

    private HardwareControllerService hardwareControllerService;
    private ControllerCommunicationService controllerCommunicationService;

    public GetInitialState(HardwareControllerService hardwareControllerService,
                           ControllerCommunicationService controllerCommunicationService){
        this.hardwareControllerService = hardwareControllerService;
        this.controllerCommunicationService = controllerCommunicationService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");

        HardwareController hardwareController = this.hardwareControllerService.getHardwareControllerBySerialNumber(serialNumber);
        if(hardwareController != null){
            this.controllerCommunicationService.sendInitialState(hardwareController.getId());
        }
    }
}
