package frentz.daniel.controller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sensor.rabbit")
public class SensorRabbitConfig {

    private RabbitConfig registration;
    private RabbitConfig reading;

    public RabbitConfig getRegistration() {
        return registration;
    }

    public void setRegistration(RabbitConfig registration) {
        this.registration = registration;
    }

    public RabbitConfig getReading() {
        return reading;
    }

    public void setReading(RabbitConfig reading) {
        this.reading = reading;
    }
}
