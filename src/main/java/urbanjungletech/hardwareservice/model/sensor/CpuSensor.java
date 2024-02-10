package urbanjungletech.hardwareservice.model.sensor;

import jakarta.validation.constraints.NotNull;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;

public class CpuSensor extends Sensor {
    @NotNull
    private CpuSensorType sensorType;

    public CpuSensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(CpuSensorType sensorType) {
        this.sensorType = sensorType;
    }
}
