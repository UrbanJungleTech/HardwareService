package frentz.daniel.hardwareservice.service.controllercommunication.implementation;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationServiceImplementation;

import java.util.List;

public class RGBControllerCommunicationService implements ControllerCommunicationServiceImplementation {
    @Override
    public void sendStateToController(Hardware hardware) {

    }

    @Override
    public void sendInitialState(String serialNumber, List<Hardware> allHardware) {

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
