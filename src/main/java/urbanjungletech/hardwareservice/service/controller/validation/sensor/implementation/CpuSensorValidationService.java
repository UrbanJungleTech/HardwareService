package urbanjungletech.hardwareservice.service.controller.validation.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.hardwarecontroller.CpuHardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SpecificSensorValidationService;

import java.util.Arrays;

@Service
public class CpuSensorValidationService implements SpecificSensorValidationService<CpuHardwareController> {
    @Override
    public SensorValidationError validateSensorType(Sensor sensor) {
        return new SensorValidationError();
    }
}
