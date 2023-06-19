package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.service.HardwareQueueService;

import java.util.List;

public class HardwareQueueServiceAzure implements HardwareQueueService {
    @Override
    public void sendStateToController(Hardware hardware) {
    }

    @Override
    public void sendInitialState(String serialNumber, List<HardwareEntity> allHardware) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        return 0;
    }

    @Override
    public double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts) {
        return 0;
    }

    @Override
    public void registerHardware(Hardware hardware) {

    }

    @Override
    public void registerSensor(Sensor sensor) {

    }

    @Override
    public void deregisterHardware(Hardware hardware) {

    }

    @Override
    public void deregisterSensor(Sensor sensor) {

    }
}
