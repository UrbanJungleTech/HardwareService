package urbanjungletech.hardwareservice.entity.hardware.mqtt;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import urbanjungletech.hardwareservice.entity.hardware.MqttHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

@Entity
public class SimpleHardwareEntity extends MqttHardwareEntity {
    @OneToOne
    private MqttPinEntity pin;

    public MqttPinEntity getPin() {
        return pin;
    }

    public void setPin(MqttPinEntity pin) {
        this.pin = pin;
    }
}
