package urbanjungletech.hardwareservice.jsonrpc.method;

import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import urbanjungletech.hardwareservice.model.Hardware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeregisterHardware implements RpcMethod {

    private HardwareAdditionService hardwareAdditionService;
    private HardwareQueryService hardwareQueryService;

    public DeregisterHardware(HardwareAdditionService hardwareAdditionService,
                              HardwareQueryService hardwareQueryService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");
        int hardwarePort = (int)params.get("port");
        Hardware hardware = this.hardwareQueryService.getHardware(serialNumber, hardwarePort);
        this.hardwareAdditionService.delete(hardware.getId());
    }
}
