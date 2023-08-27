package urbanjungletech.hardwareservice.jsonrpc.method;

import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;
import urbanjungletech.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetInitialState implements RpcMethod{

    private HardwareControllerQueryService hardwareControllerQueryService;
    private ControllerCommunicationService controllerCommunicationService;

    public GetInitialState(HardwareControllerQueryService hardwareControllerQueryService,
                           ControllerCommunicationService controllerCommunicationService){
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.controllerCommunicationService = controllerCommunicationService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");

        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareControllerBySerialNumber(serialNumber);
        if(hardwareController != null){
            this.controllerCommunicationService.sendInitialState(hardwareController.getId());
        }
    }
}
