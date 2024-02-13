package urbanjungletech.hardwareservice.model.hardwarecontroller;

public class MqttHardwareController extends HardwareController {
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
