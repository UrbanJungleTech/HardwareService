package urbanjungletech.hardwareservice.model.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import urbanjungletech.hardwareservice.model.validation.validator.MqttBrokerUrlValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MqttBrokerUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMqttBrokerUrl {
    String message() default "Invalid MQTT broker URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
