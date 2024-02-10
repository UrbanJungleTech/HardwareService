package urbanjungletech.hardwareservice.service.controller.controllercommunication;

import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

public interface ControllerCommunicationService {
    void sendStateToController(Hardware hardware);
    void sendInitialState(long hardwareControllerId);

    double getSensorReading(Sensor sensor);

    double getAverageSensorReading(String[] sensorPorts);

    void registerHardware(Hardware hardware);

    void registerSensor(Sensor sensor);

    void deregisterHardware(Hardware hardware);
    void deregisterSensor(Sensor sensor);
}
