package frentz.daniel.hardwareservice.jsonrpc.method;

import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetInitialState implements RpcMethod{

    private HardwareControllerService hardwareControllerService;
    private HardwareQueueService hardwareQueueService;

    public GetInitialState(HardwareControllerService hardwareControllerService,
                           HardwareQueueService hardwareQueueService){
        this.hardwareControllerService = hardwareControllerService;
        this.hardwareQueueService = hardwareQueueService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");

        HardwareController hardwareController = this.hardwareControllerService.getHardwareControllerBySerialNumber(serialNumber);
        if(hardwareController != null){
            this.hardwareQueueService.sendInitialState(serialNumber, hardwareController.getHardware());
        }
    }
}
