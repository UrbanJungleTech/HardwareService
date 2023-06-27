package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
