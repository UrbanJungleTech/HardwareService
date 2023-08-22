package urbanjungletech.hardwareservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "entity")
public class EntityNameConfiguration {
    private HashMap<String, String> names;

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(HashMap<String, String> names) {
        this.names = names;
    }
}
