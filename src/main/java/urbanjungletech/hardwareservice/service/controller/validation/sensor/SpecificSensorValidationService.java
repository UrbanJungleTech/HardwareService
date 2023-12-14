package urbanjungletech.hardwareservice.service.controller.validation.sensor;

import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.implementation.SensorValidationError;

public interface SpecificSensorValidationService<HardwareControllerType> {
    SensorValidationError validateSensorType(Sensor sensor);
}
