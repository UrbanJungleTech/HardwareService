package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
public class RegisterHardware implements RpcMethod {

    private ObjectMapper objectMapper;
    private HardwareControllerAdditionService hardwareAdditionService;
    private HardwareControllerDAO hardwareControllerDAO;
    private HardwareControllerQueryService hardwareControllerQueryService;

    public RegisterHardware(HardwareControllerAdditionService hardwareAdditionService,
                            ObjectMapper objectMapper,
                            HardwareControllerDAO hardwareControllerDAO,
                            HardwareControllerQueryService hardwareControllerQueryService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.objectMapper = objectMapper;
        this.hardwareControllerDAO = hardwareControllerDAO;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }
    @Override
    public void process(Map<String, Object> params) {
        Hardware hardware = objectMapper.convertValue(params.get("hardware"), Hardware.class);
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareControllerBySerialNumber(params.get("serialNumber").toString());
        hardwareAdditionService.addHardware(hardwareController.getId(), hardware);
    }
}
