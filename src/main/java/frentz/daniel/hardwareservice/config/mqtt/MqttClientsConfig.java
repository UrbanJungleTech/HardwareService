package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.config.ControllerConfiguration;
import frentz.daniel.hardwareservice.config.MqttClientConfiguration;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqttClientsConfig {
    @Bean("MqttClients")
    public Map<String, IMqttClient> clients(ControllerConfiguration controllerConfiguration) throws MqttException {
        Map<String, IMqttClient> result = new HashMap<>();
        for(String clientName : controllerConfiguration.getClients().getMqtt().keySet()) {
            MqttClientConfiguration clientConfiguration = controllerConfiguration.getClients().getMqtt().get(clientName);
            IMqttClient client = new MqttClient(clientConfiguration.getServer(), clientConfiguration.getClientId());
            result.put(clientName, client);
        }
        return result;
    }
}
