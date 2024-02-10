package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.sensor.CpuSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.hardwarecontroller.CpuHardwareController;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.SpecificControllerCommunicationService;

import java.util.Map;
import java.util.function.Supplier;

@Service
public class CpuSpecificControllerCommunicationService implements SpecificControllerCommunicationService<CpuHardwareController> {

    private final Map<CpuSensorType, Supplier<Double>> sensorTypeMappings;

    public CpuSpecificControllerCommunicationService(@Qualifier("cpuSensorTypeMappings") Map<CpuSensorType, Supplier<Double>> sensorTypeMappings) {
        this.sensorTypeMappings = sensorTypeMappings;
    }

    @Override
    public void sendStateToController(Hardware hardware) {

    }

    @Override
    public void sendInitialState(long hardwareControllerId) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        double result = this.sensorTypeMappings.get(((CpuSensor)sensor).getSensorType()).get();
        return result;
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

}
