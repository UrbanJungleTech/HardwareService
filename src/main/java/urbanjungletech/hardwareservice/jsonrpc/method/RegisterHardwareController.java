package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

import java.util.Map;

@Service
public class RegisterHardwareController implements RpcMethod{

    private ObjectMapper objectMapper;
    private HardwareControllerAdditionService hardwareControllerAdditionService;

    public RegisterHardwareController(ObjectMapper objectMapper,
                                      HardwareControllerAdditionService hardwareControllerAdditionService){
        this.objectMapper = objectMapper;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @Override
    public void process(Map<String, Object> params) {
        HardwareController hardwareController = this.objectMapper.convertValue(params.get("hardwareController"), HardwareController.class);
        this.hardwareControllerAdditionService.create(hardwareController);
    }
}
