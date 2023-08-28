package urbanjungletech.hardwareservice.service.controller.validation.sensor;

import urbanjungletech.hardwareservice.model.Sensor;

public interface SensorValidationService {
    SensorValidationError validateSensorType(Sensor sensor);
}
