package urbanjungletech.hardwareservice.helpers.services.mqtt.mockclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "development.mqtt.client")
public class MqttMockClientConfig {
    private String serialNumber;
    private boolean enabled;
    private Map<String, String> callbacks;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, String> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(Map<String, String> callbacks) {
        this.callbacks = callbacks;
    }
}
