package urbanjungletech.hardwareservice.model.validation.annotation;

import jakarta.validation.Constraint;
import urbanjungletech.hardwareservice.model.validation.validator.CpuSensorTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpuSensorTypeValidator.class)
public @interface CpuSensorTypeValidation {
    String message() default "Invalid CPU sensor type";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
