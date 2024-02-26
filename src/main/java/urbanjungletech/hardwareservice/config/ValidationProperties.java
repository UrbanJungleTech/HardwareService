package urbanjungletech.hardwareservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "validation")
@Component
public class ValidationProperties {
    private List<String> mqttBrokerUrlSchemas;

    public List<String> getMqttBrokerUrlSchemas() {
        return mqttBrokerUrlSchemas;
    }

    public void setMqttBrokerUrlSchemas(List<String> mqttBrokerUrlSchemas) {
        this.mqttBrokerUrlSchemas = mqttBrokerUrlSchemas;
    }
}
