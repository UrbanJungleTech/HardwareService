package urbanjungletech.hardwareservice.mock.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.HashMap;
import java.util.Map;

@Service
@HardwareControllerCommunicationService(type="mock")
public class MockHardwareControllerCommunicationService extends ControllerCommunicationServiceImplementation {
    private Map<String, Integer> callCounter;

    public MockHardwareControllerCommunicationService() {
        this.callCounter = new HashMap<>();
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        this.callCounter.put("getSensorReading", this.callCounter.getOrDefault("getSensorReading", 0) + 1);
        return 1.0;
    }

    public Map<String, Integer> getCallCounter() {
        return callCounter;
    }

    public void setCallCounter(Map<String, Integer> callCounter) {
        this.callCounter = callCounter;
    }
}
