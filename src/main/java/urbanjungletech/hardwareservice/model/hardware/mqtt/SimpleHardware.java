package urbanjungletech.hardwareservice.model.hardware.mqtt;

import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.MqttHardware;

/**
 * Represents a hardware which has only one pin.
 */
public class SimpleHardware extends Hardware {
    private MqttPin pin;

    public MqttPin getPin() {
        return pin;
    }

    public void setPin(MqttPin pin) {
        this.pin = pin;
    }
}
