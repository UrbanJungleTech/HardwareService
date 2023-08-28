package urbanjungletech.hardwareservice.service.controller.validation.sensor;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.CpuSensorType;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.Arrays;

@Service
@HardwareControllerCommunicationService(type = "cpu")
public class CpuSensorValidationService implements SensorValidationServiceImplementation{
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
