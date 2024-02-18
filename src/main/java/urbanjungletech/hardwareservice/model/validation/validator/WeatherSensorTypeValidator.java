package urbanjungletech.hardwareservice.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import urbanjungletech.hardwareservice.model.sensor.WeatherSensor;
import urbanjungletech.hardwareservice.model.validation.annotation.WeatherSensorTypeValidation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherSensorTypes;

import java.util.Arrays;

public class WeatherSensorTypeValidator implements ConstraintValidator<WeatherSensorTypeValidation, WeatherSensor> {

    @Override
    public boolean isValid(WeatherSensor weatherSensor, ConstraintValidatorContext constraintValidatorContext) {
        if(weatherSensor.getPort() == null){
            constraintValidatorContext
                    .disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("Port cannot be null")
                    .addPropertyNode("port")
                    .addConstraintViolation();
            return false;
        }
        if(Arrays.stream(WeatherSensorTypes.values()).noneMatch((portType) -> portType.toString().equals(weatherSensor.getPort()))){
            constraintValidatorContext
                    .disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("Invalid weather sensor type")
                    .addPropertyNode("port")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
