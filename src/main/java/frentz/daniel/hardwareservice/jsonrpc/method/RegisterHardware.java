package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterHardware implements RpcMethod {

    private ObjectMapper objectMapper;
    private HardwareAdditionService hardwareAdditionService;

    public RegisterHardware(HardwareAdditionService hardwareAdditionService, ObjectMapper objectMapper){
        this.hardwareAdditionService = hardwareAdditionService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Map<String, Object> params) {
        Hardware hardware = objectMapper.convertValue(params.get("hardware"), Hardware.class);
        hardwareAdditionService.create(hardware);
    }
}
