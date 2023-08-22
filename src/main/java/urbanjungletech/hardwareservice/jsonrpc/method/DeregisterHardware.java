package urbanjungletech.hardwareservice.jsonrpc.method;

import urbanjungletech.hardwareservice.addition.HardwareAdditionService;
import urbanjungletech.hardwareservice.service.HardwareService;
import urbanjungletech.hardwareservice.model.Hardware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeregisterHardware implements RpcMethod {

    private HardwareAdditionService hardwareAdditionService;
    private HardwareService hardwareService;

    public DeregisterHardware(HardwareAdditionService hardwareAdditionService,
                              HardwareService hardwareService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareService = hardwareService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");
        int hardwarePort = (int)params.get("port");
        Hardware hardware = this.hardwareService.getHardware(serialNumber, hardwarePort);
        this.hardwareAdditionService.delete(hardware.getId());
    }
}
