package urbanjungletech.hardwareservice.entity.hardware.mqtt;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

@Entity
public class TriColourLedEntity extends HardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private MqttPinEntity redPin;
    @ManyToOne
    private MqttPinEntity greenPin;
    @ManyToOne
    private MqttPinEntity bluePin;

    public MqttPinEntity getRedPin() {
        return redPin;
    }

    public void setRedPin(MqttPinEntity redPin) {
        this.redPin = redPin;
    }

    public MqttPinEntity getGreenPin() {
        return greenPin;
    }

    public void setGreenPin(MqttPinEntity greenPin) {
        this.greenPin = greenPin;
    }

    public MqttPinEntity getBluePin() {
        return bluePin;
    }

    public void setBluePin(MqttPinEntity bluePin) {
        this.bluePin = bluePin;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
