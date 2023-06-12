package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterHardwareController implements RpcMethod{

    private ObjectMapper objectMapper;
    private HardwareControllerAdditionService hardwareControllerAdditionService;

    public RegisterHardwareController(ObjectMapper objectMapper, HardwareControllerAdditionService hardwareControllerAdditionService){
        this.objectMapper = objectMapper;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @Override
    public void process(Map<String, Object> params) {
        HardwareController hardwareController = this.objectMapper.convertValue(params.get("hardwareController"), HardwareController.class);
        this.hardwareControllerAdditionService.create(hardwareController);
    }

    @Override
    public String getName() {
        return "RegisterHardwareController";
    }
}
