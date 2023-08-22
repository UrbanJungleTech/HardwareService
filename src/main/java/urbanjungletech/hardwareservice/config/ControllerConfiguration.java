package urbanjungletech.hardwareservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "controller")
public class ControllerConfiguration {
    private Map<String, ControllerTypeConfiguration> types;
    private ControllerClientConfiguration clients;


    public Map<String, ControllerTypeConfiguration> getTypes() {
        return types;
    }

    public void setTypes(Map<String, ControllerTypeConfiguration> types) {
        this.types = types;
    }

    public ControllerClientConfiguration getClients() {
        return clients;
    }

    public void setClients(ControllerClientConfiguration clients) {
        this.clients = clients;
    }
}
