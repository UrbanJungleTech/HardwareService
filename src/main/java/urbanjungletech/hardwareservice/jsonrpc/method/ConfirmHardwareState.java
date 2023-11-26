package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmHardwareState implements RpcMethod {

    private final ObjectMapper objectMapper;
    private final HardwareAdditionService hardwareAdditionService;
    private final HardwareDAO hardwareDAO;
    private final HardwareConverter hardwareConverter;
    private final HardwareStateConverter hardwareStateConverter;
    private final HardwareQueryService hardwareQueryService;

    public ConfirmHardwareState(HardwareDAO hardwareDAO,
                                HardwareAdditionService hardwareAdditionService,
                                ObjectMapper objectMapper,
                                HardwareConverter hardwareConverter,
                                HardwareStateConverter hardwareStateConverter,
                                HardwareQueryService hardwareQueryService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.objectMapper = objectMapper;
        this.hardwareDAO = hardwareDAO;
        this.hardwareConverter = hardwareConverter;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Override
    public void process(Map<String, Object> params) {
        HashMap<String, Object> hardwareStateJson = (HashMap<String, Object>) params.get("hardwareState");
        HardwareState hardwareState = objectMapper.convertValue(hardwareStateJson, HardwareState.class);
        String serialNumber = (String)params.get("serialNumber");
        String port = (String)params.get("port");
        Hardware hardware = this.hardwareQueryService.getHardware(serialNumber, port);
        hardware.setCurrentState(hardwareState);
        this.hardwareAdditionService.update(hardware.getId(), hardware);
    }
}
