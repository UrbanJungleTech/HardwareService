package urbanjungletech.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientsConfig {
    @Bean("MqttClients")
    public Map<Long, IMqttClient> clients() {
        Map<Long, IMqttClient> result = new HashMap<>();
        return result;
    }
}
