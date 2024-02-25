package urbanjungletech.hardwareservice.model.hardware.mqtt;

import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.Map;

/**
 * Represents a hardware which has multiple pins. Each pin can be controlled individually,
 * but when there is an update the entire hardware will be sent to the controller at once.
 */
public class StatefulHardware extends Hardware {
    protected Map<String, MqttPin> pins;

    public Map<String, MqttPin> getPins() {
        return pins;
    }

    public void setPins(Map<String, MqttPin> pins) {
        this.pins = pins;
    }
}
