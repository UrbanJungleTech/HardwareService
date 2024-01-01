package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

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
        objectMapper.registerSubtypes(new NamedType(MqttHardwareController.class, "MqttHardwareController"));
        HardwareController hardwareController = this.objectMapper.convertValue(params.get("hardwareController"), MqttHardwareController.class);
        this.hardwareControllerAdditionService.create(hardwareController);
    }
}
