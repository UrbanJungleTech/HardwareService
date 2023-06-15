package frentz.daniel.hardwareservice.config.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    private Map<String, String> methods;

    public Map<String, String> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, String> methods) {
        this.methods = methods;
    }
}
