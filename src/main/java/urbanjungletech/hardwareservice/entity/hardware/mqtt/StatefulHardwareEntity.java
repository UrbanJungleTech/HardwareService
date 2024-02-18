package urbanjungletech.hardwareservice.entity.hardware.mqtt;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

import java.util.Map;

@Entity
public class StatefulHardwareEntity extends HardwareEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "stateful_hardware_pins",
            joinColumns = @JoinColumn(name = "stateful_hardware_id"),
            inverseJoinColumns = @JoinColumn(name = "mqtt_pin_id"))
    @MapKeyColumn(name = "pin_name")
    private Map<String, MqttPinEntity> pins;

    public Map<String, MqttPinEntity> getPins() {
        return pins;
    }

    public void setPins(Map<String, MqttPinEntity> pins) {
        this.pins = pins;
    }
}
