package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterHardware implements RpcMethod {

    private ObjectMapper objectMapper;
    private HardwareControllerAdditionService hardwareAdditionService;
    private HardwareControllerDAO hardwareControllerDAO;

    public RegisterHardware(HardwareControllerAdditionService hardwareAdditionService,
                            ObjectMapper objectMapper,
                            HardwareControllerDAO hardwareControllerDAO){
        this.hardwareAdditionService = hardwareAdditionService;
        this.objectMapper = objectMapper;
        this.hardwareControllerDAO = hardwareControllerDAO;
    }
    @Override
    public void process(Map<String, Object> params) {
        Hardware hardware = objectMapper.convertValue(params.get("hardware"), Hardware.class);
        hardwareAdditionService.addHardware(1L, hardware);
    }
}
