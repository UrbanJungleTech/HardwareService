package frentz.daniel.hardwareservice.config.mqtt;


import io.moquette.broker.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnProperty(name = "local.mqtt.enabled", havingValue = "true")
public class MockMqttServer {

    @Bean
    public Properties mqttProperties(MockMqttProperties mockMqttProperties){
        Properties result = new Properties();
        result.setProperty("port", mockMqttProperties.getPort());
        result.setProperty("host", mockMqttProperties.getHost());
        return result;
    }
    @Bean
    public Server mqttServer() {
        Server result = new Server();
        return result;
    }

}
