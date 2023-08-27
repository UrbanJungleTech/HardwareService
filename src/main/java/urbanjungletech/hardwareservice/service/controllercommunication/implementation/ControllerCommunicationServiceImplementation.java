package urbanjungletech.hardwareservice.service.controllercommunication.implementation;

import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controllercommunication.ControllerCommunicationService;

public class ControllerCommunicationServiceImplementation implements ControllerCommunicationService {
    @Override
    public void sendStateToController(Hardware hardware) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendInitialState(long hardwareControllerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerHardware(Hardware hardware) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerSensor(Sensor sensor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        throw new UnsupportedOperationException();
    }
}
