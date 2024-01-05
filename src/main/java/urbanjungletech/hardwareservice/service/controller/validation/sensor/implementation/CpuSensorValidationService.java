package urbanjungletech.hardwareservice.service.controller.validation.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.hardwarecontroller.CpuHardwareController;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SpecificSensorValidationService;

import java.util.Arrays;

@Service
public class CpuSensorValidationService implements SpecificSensorValidationService<CpuHardwareController> {
    @Override
    public SensorValidationError validateSensorType(Sensor sensor) {
        String sensorType = sensor.getConfiguration().get("sensorType");
        if(Arrays.stream(CpuSensorType.values()).noneMatch(cpuSensorType -> cpuSensorType.name().equals(sensorType))){
            SensorValidationError sensorValidationError = new SensorValidationError();
            sensorValidationError.setMessages(Arrays.asList("Sensor type {} is not valid", sensorType));
            return sensorValidationError;
        }
        return new SensorValidationError();
    }
}
