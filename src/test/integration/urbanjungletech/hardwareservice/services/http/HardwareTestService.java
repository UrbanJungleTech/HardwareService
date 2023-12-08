package urbanjungletech.hardwareservice.services.http;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;

@Service
public class HardwareTestService {

    private HardwareControllerTestService hardwareControllerTestService;

    public HardwareTestService(HardwareControllerTestService hardwareControllerTestService) {
        this.hardwareControllerTestService = hardwareControllerTestService;
    }

    public HardwareController createBasicHardware() throws Exception{
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardware.setName("hardware1");
        hardware.setType("temperature");
        HardwareController result = this.hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
        return result;
    }

    public HardwareController createBasicHardwareWithTimer() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setPort("1");
        hardware.setName("hardware1");
        hardware.setType("temperature");
        Timer timer = new Timer();
        timer.setCronString("0 0 0 1 1 ? 2099");
        timer.setLevel(100);
        hardware.getTimers().add(timer);
        HardwareController result = this.hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
        return result;
    }

    public HardwareController createBasicHardwareWithTimers(List<Timer> timers) throws Exception {
        Hardware hardware = new Hardware();
        hardware.setTimers(timers);
        HardwareController result = this.hardwareControllerTestService.addBasicHardwareControllerWithHardware(List.of(hardware));
        return result;
    }
}
