package frentz.daniel.controller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hardware.rabbit")
public class HardwareRabbitConfig {
    private RabbitConfig state;
    private RabbitConfig stateConfirmation;
    private RabbitConfig registration;
    private RabbitConfig stateChange;

    public RabbitConfig getState() {
        return state;
    }

    public void setState(RabbitConfig state) {
        this.state = state;
    }

    public RabbitConfig getStateConfirmation() {
        return stateConfirmation;
    }

    public void setStateConfirmation(RabbitConfig stateConfirmation) {
        this.stateConfirmation = stateConfirmation;
    }

    public RabbitConfig getRegistration() {
        return registration;
    }

    public void setRegistration(RabbitConfig registration) {
        this.registration = registration;
    }

    public RabbitConfig getStateChange() {
        return stateChange;
    }

    public void setStateChange(RabbitConfig stateChange) {
        this.stateChange = stateChange;
    }
}
