package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;

import java.util.List;

public class HardwareQueueServiceAzure implements HardwareQueueService{
    @Override
    public void sendStateToController(HardwareEntity hardware) {
    }

    @Override
    public void sendInitialState(String serialNumber, List<HardwareEntity> allHardware) {

    }

    @Override
    public double getSensorReading(SensorEntity sensor) {
        return 0;
    }

    @Override
    public double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts) {
        return 0;
    }

    @Override
    public void registerHardware(HardwareEntity hardware) {

    }

    @Override
    public void registerSensor(SensorEntity sensor) {

    }

    @Override
    public void deregisterHardware(HardwareEntity hardware) {

    }

    @Override
    public void deregisterSensor(SensorEntity sensor) {

    }
}
