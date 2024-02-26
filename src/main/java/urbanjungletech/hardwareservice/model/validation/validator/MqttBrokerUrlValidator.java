package urbanjungletech.hardwareservice.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.config.ValidationProperties;
import urbanjungletech.hardwareservice.model.validation.annotation.ValidMqttBrokerUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Validator for the {@link ValidMqttBrokerUrl} annotation.
 * Checks that the given URL is a valid MQTT broker URL.
 * Note that this validator does not check if the broker is reachable.
 */
@Service
public class MqttBrokerUrlValidator implements ConstraintValidator<ValidMqttBrokerUrl, String> {

    private final ValidationProperties validationProperties;

    public MqttBrokerUrlValidator(ValidationProperties validationProperties) {
        this.validationProperties = validationProperties;
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        try {
            if(url == null){
                return false;
            }
            if(!validationProperties.getMqttBrokerUrlSchemas().stream().anyMatch(url::startsWith)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid URL schema " + url.split("://")[0]).addConstraintViolation();
                return false;
            }
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid URL").addConstraintViolation();
            return false;
        }
    }
}
