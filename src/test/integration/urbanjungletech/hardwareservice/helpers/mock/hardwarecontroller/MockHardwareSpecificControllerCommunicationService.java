package urbanjungletech.hardwareservice.helpers.mock.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.SpecificControllerCommunicationService;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockHardwareSpecificControllerCommunicationService implements SpecificControllerCommunicationService<MockHardwareController> {
    private Map<String, Integer> callCounter;

    public MockHardwareSpecificControllerCommunicationService() {
        this.callCounter = new HashMap<>();
    }

    @Override
    public void sendStateToController(Hardware hardware) {

    }

    @Override
    public void sendInitialState(long hardwareControllerId) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        this.callCounter.put("getSensorReading", this.callCounter.getOrDefault("getSensorReading", 0) + 1);
        return 1.0;
    }

    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
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

    public Map<String, Integer> getCallCounter() {
        return callCounter;
    }

    public void setCallCounter(Map<String, Integer> callCounter) {
        this.callCounter = callCounter;
    }
}
