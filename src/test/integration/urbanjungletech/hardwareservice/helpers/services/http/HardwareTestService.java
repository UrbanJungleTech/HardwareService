package urbanjungletech.hardwareservice.helpers.services.http;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.MqttHardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

import java.util.List;

@Service
public class HardwareTestService {

    private HardwareControllerTestService hardwareControllerTestService;

    public HardwareTestService(HardwareControllerTestService hardwareControllerTestService) {
        this.hardwareControllerTestService = hardwareControllerTestService;
    }

    public HardwareController createMqttHardwareControllerWithDefaultHardware() throws Exception{
        Hardware hardware = new MqttHardware();
        hardware.setPort("1");
        hardware.setName("hardware1");
        hardware.setType("temperature");
        hardware.setPossibleStates(List.of("on", "off"));
        hardware.setOffState("off");
        HardwareController result = this.hardwareControllerTestService.createMqttHardwareController(List.of(hardware));
        return result;
    }

    public HardwareController createMqttHardwareControllerWithDefaultHardwareAndTimer() throws Exception {
        Hardware hardware = new MqttHardware();
        hardware.setPort("1");
        hardware.setName("hardware1");
        hardware.setType("temperature");
        hardware.setPossibleStates(List.of("on", "off"));
        hardware.setOffState("off");
        Timer timer = new Timer();
        timer.setCronString("0 0 0 1 1 ? 2099");
        timer.setLevel(100);
        hardware.getTimers().add(timer);
        HardwareController result = this.hardwareControllerTestService.createMqttHardwareController(List.of(hardware));
        return result;
    }
}
