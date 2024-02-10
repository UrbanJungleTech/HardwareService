package urbanjungletech.hardwareservice.model.hardwarecontroller;

import urbanjungletech.hardwareservice.model.hardware.MqttHardware;
import urbanjungletech.hardwareservice.model.sensor.MqttSensor;

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
