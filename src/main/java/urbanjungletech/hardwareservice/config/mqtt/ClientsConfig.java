package urbanjungletech.hardwareservice.config.mqtt;

import urbanjungletech.hardwareservice.config.ControllerConfiguration;
import urbanjungletech.hardwareservice.config.MqttClientConfiguration;
import urbanjungletech.hardwareservice.config.OpenWeatherClientConfiguration;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
