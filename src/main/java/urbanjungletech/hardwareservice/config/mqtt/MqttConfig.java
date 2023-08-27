package urbanjungletech.hardwareservice.config.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    public Map<String, String> getListeners() {
        Map result = new HashMap<String, String>();
        result.put("1234ToMicrocontroller", "urbanjungletech.hardwareservice.config.mqtt.mockclient.MockMqttClientListener");
        result.put("1234ToMicrocontroller", "urbanjungletech.hardwareservice.config.mqtt.listener.MqttCacheListener");
        result.put("FromMicrocontroller", "urbanjungletech.hardwareservice.config.mqtt.listener.MicrocontrollerMessageListener");
        return result;
    }
}
