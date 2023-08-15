package frentz.daniel.hardwareservice.service.controllercommunication;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.Sensor;

import java.util.List;

public interface ControllerCommunicationService {
    void sendStateToController(Hardware hardware);
    void sendInitialState(String serialNumber, List<Hardware> allHardware);

    double getSensorReading(Sensor sensor);

    double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts);

    void registerHardware(Hardware hardware);

    void registerSensor(Sensor sensor);

    void deregisterHardware(Hardware hardware);
    void deregisterSensor(Sensor sensor);
}
