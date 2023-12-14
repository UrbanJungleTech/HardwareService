package urbanjungletech.hardwareservice.helpers.services.mqtt;

import io.moquette.broker.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.config.mqtt.MockMqttProperties;

import java.util.Properties;

@Configuration
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

    @Bean
    public DisposableBean mqttServerShutdownHook(Server mqttServer) {
        return () -> {
            mqttServer.stopServer();
        };
    }

}
