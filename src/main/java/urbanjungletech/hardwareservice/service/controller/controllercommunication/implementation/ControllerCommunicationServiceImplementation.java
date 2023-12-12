package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import urbanjungletech.hardwareservice.exception.exception.UnsupportedControllerOperationException;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;

public class ControllerCommunicationServiceImplementation implements ControllerCommunicationService {
    @Override
    public void sendStateToController(Hardware hardware) {

    }

    @Override
    public void sendInitialState(long hardwareControllerId) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        return 1.0;
    }

    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
        return 2.0;
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
