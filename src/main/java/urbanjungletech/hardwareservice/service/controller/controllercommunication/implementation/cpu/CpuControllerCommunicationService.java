package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.Map;
import java.util.function.Supplier;

@Service()
@HardwareControllerCommunicationService(type="cpu")
public class CpuControllerCommunicationService extends ControllerCommunicationServiceImplementation {

    private final Map<CpuSensorType, Supplier<Double>> sensorTypeMappings;

    public CpuControllerCommunicationService(@Qualifier("cpuSensorTypeMappings") Map<CpuSensorType, Supplier<Double>> sensorTypeMappings) {
        this.sensorTypeMappings = sensorTypeMappings;
    }
    @Override
    public double getSensorReading(Sensor sensor) {
        CpuSensorType cpuSensorType = CpuSensorType.valueOf(sensor.getConfiguration().get("sensorType"));
        double result = this.sensorTypeMappings.get(cpuSensorType).get();
        return result;
    }

}
