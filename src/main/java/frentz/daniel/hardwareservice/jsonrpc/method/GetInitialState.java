package frentz.daniel.hardwareservice.jsonrpc.method;

import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetInitialState implements RpcMethod{

    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareQueueService hardwareQueueService;

    public GetInitialState(HardwareControllerDAO hardwareControllerDAO,
                           HardwareQueueService hardwareQueueService){
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareQueueService = hardwareQueueService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");
        HardwareControllerEntity hardwareController = this.hardwareControllerDAO.getBySerialNumber(serialNumber);
        if(hardwareController != null){
            this.hardwareQueueService.sendInitialState(serialNumber, hardwareController.getHardware());
        }
    }

    @Override
    public String getName() {
        return "GetInitialState";
    }
}
