package urbanjungletech.hardwareservice.entity.hardwarecontroller;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class MqttHardwareControllerEntity extends HardwareControllerEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private HardwareMqttClientEntity hardwareMqttClient;

    public HardwareMqttClientEntity getHardwareMqttClient() {
        return hardwareMqttClient;
    }

    public void setHardwareMqttClient(HardwareMqttClientEntity hardwareMqttClient) {
        this.hardwareMqttClient = hardwareMqttClient;
    }
}
