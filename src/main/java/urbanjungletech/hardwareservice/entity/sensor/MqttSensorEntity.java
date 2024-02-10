package urbanjungletech.hardwareservice.entity.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MqttSensorEntity extends SensorEntity {
    private String sensorType;

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }
}
