package urbanjungletech.hardwareservice.exception.exception;

import urbanjungletech.hardwareservice.service.controller.validation.sensor.implementation.SensorValidationError;

public class InvalidSensorConfigurationException extends RuntimeException {
    public InvalidSensorConfigurationException(SensorValidationError validationError) {
    }
}
