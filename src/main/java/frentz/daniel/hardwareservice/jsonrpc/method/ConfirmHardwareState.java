package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmHardwareState implements RpcMethod {

    private final ObjectMapper objectMapper;
    private final HardwareAdditionService hardwareAdditionService;
    private final HardwareDAO hardwareDAO;
    private final HardwareConverter hardwareConverter;
    private final HardwareStateConverter hardwareStateConverter;
    private final HardwareService hardwareService;

    public ConfirmHardwareState(HardwareDAO hardwareDAO,
                                HardwareAdditionService hardwareAdditionService,
                                ObjectMapper objectMapper,
                                HardwareConverter hardwareConverter,
                                HardwareStateConverter hardwareStateConverter,
                                HardwareService hardwareService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.objectMapper = objectMapper;
        this.hardwareDAO = hardwareDAO;
        this.hardwareConverter = hardwareConverter;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareService = hardwareService;
    }

    @Override
    public void process(Map<String, Object> params) {
        HashMap<String, Object> hardwareStateJson = (HashMap<String, Object>) params.get("hardwareState");
        HardwareState hardwareState = objectMapper.convertValue(hardwareStateJson, HardwareState.class);
        String serialNumber = (String)params.get("serialNumber");
        Long port = Long.valueOf((Integer)params.get("port"));
        Hardware hardware = this.hardwareService.getHardware(serialNumber, port);
        hardware.setCurrentState(hardwareState);
        this.hardwareAdditionService.update(hardware.getId(), hardware);
    }
}
