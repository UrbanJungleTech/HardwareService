package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;

import java.util.List;

public interface HardwareQueueService {
    void sendStateToController(HardwareEntity hardware);
    void sendInitialState(String serialNumber, List<HardwareEntity> allHardware);
    double getSensorReading(SensorEntity sensor);
    double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts);

    void registerHardware(HardwareEntity hardware);

    void registerSensor(SensorEntity sensor);

    void deregisterHardware(HardwareEntity hardware);
    void deregisterSensor(SensorEntity sensor);
}
