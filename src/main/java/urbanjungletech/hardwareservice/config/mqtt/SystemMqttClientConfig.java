package urbanjungletech.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SystemMqttClientConfig {
    private RpcProperties rpcProperties;

    public SystemMqttClientConfig(RpcProperties rpcProperties) {
        this.rpcProperties = rpcProperties;
    }
    @Bean
    public Map<SystemMqttClientProperties, IMqttClient> serverMqttClients() throws MqttException {
        Map<SystemMqttClientProperties, IMqttClient> result = new HashMap<>();
        return result;
    }

    @Bean("defaultSystemMqttClient")
    @ConditionalOnProperty(prefix = "mqtt-rpc", name = "enabled", havingValue = "true")
    public IMqttClient defaultSystemMqttClient() throws MqttException {
        String serverUri = rpcProperties.getUri();
        String serverQueue = rpcProperties.getQueue();
        IMqttClient result = new MqttClient(serverUri, serverQueue, null);
        return result;
    }

}
