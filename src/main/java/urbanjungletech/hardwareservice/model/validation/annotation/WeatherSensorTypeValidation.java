package urbanjungletech.hardwareservice.model.validation.annotation;

import jakarta.validation.Constraint;
import urbanjungletech.hardwareservice.model.validation.validator.WeatherSensorTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WeatherSensorTypeValidator.class)
public @interface WeatherSensorTypeValidation {
    String message() default "Weather sensor type validation failed";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
