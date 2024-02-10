package urbanjungletech.hardwareservice.entity.sensor;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;

@Entity
public class CpuSensorEntity extends SensorEntity{
    private CpuSensorType sensorType;

    public CpuSensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(CpuSensorType sensorType) {
        this.sensorType = sensorType;
    }
}
