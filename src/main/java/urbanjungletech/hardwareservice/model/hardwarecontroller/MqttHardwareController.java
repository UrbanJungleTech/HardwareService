package urbanjungletech.hardwareservice.model.hardwarecontroller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class MqttHardwareController extends HardwareController {
    @Valid
    @NotNull
    private HardwareMqttClient hardwareMqttClient;
    public MqttHardwareController() {
        super();
    }

    public HardwareMqttClient getHardwareMqttClient() {
        return hardwareMqttClient;
    }

    public void setHardwareMqttClient(HardwareMqttClient hardwareMqttClient) {
        this.hardwareMqttClient = hardwareMqttClient;
    }
}
