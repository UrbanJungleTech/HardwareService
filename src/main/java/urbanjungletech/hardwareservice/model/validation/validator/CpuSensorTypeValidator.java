package urbanjungletech.hardwareservice.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import urbanjungletech.hardwareservice.model.sensor.CpuSensor;
import urbanjungletech.hardwareservice.model.validation.annotation.CpuSensorTypeValidation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;

import java.util.Arrays;

/**
 * Validator for CpuSensorTypeValidation
 * @see CpuSensorTypeValidation
 */
public class CpuSensorTypeValidator implements ConstraintValidator<CpuSensorTypeValidation, CpuSensor> {
    @Override
    public boolean isValid(CpuSensor cpuSensor, ConstraintValidatorContext constraintValidatorContext) {
        if(cpuSensor.getPort() == null){
            constraintValidatorContext
                    .disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("Port cannot be null")
                    .addPropertyNode("port")
                    .addConstraintViolation();
            return false;
        }
        if(!Arrays.stream(CpuSensorType.values()).anyMatch((portType) -> portType.toString().equals(cpuSensor.getPort()))){
            constraintValidatorContext
                    .disableDefaultConstraintViolation();
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate("Invalid CPU sensor type")
                    .addPropertyNode("port")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
