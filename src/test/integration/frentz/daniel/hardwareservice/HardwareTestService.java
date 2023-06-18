package frentz.daniel.hardwareservice;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("test")
public class HardwareTestService {

    private HardwareControllerTestService hardwareControllerTestService;

    public HardwareTestService(HardwareControllerTestService hardwareControllerTestService) {
        this.hardwareControllerTestService = hardwareControllerTestService;
    }

    public HardwareController createBasicHardware() throws Exception{
        Hardware hardware = new Hardware();
        hardware.setPort(1L);
        hardware.setName("hardware1");
        hardware.setType("temperature");
        HardwareController result = this.hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
        return result;
    }
}
