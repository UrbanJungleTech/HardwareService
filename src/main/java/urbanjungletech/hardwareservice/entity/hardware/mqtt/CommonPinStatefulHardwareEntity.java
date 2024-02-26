package urbanjungletech.hardwareservice.entity.hardware.mqtt;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import urbanjungletech.hardwareservice.model.hardware.mqtt.CommonPinStatefulHardware;
import urbanjungletech.hardwareservice.model.hardware.mqtt.CommonPinType;

@Entity
public class CommonPinStatefulHardwareEntity extends StatefulHardwareEntity{
    @OneToOne
    protected MqttPinEntity commonPin;
    protected CommonPinType commonPinType;

    public MqttPinEntity getCommonPin() {
        return commonPin;
    }

    public void setCommonPin(MqttPinEntity commonPin) {
        this.commonPin = commonPin;
    }

    public CommonPinType getCommonPinType() {
        return commonPinType;
    }

    public void setCommonPinType(CommonPinType commonPinType) {
        this.commonPinType = commonPinType;
    }
}
