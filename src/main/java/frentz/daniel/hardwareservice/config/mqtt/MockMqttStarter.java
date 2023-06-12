package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.exception.MqttMockServerStartupException;
import io.moquette.broker.Server;
import io.moquette.interception.InterceptHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
@Profile("MockMqttServer")
public class MockMqttStarter implements ApplicationListener<ContextRefreshedEvent> {
    private Server mqttServer;
    private Properties mqttProperties;
    private List<InterceptHandler> interceptHandlers;

    public MockMqttStarter(Server mqttServer,
                           Properties mqttProperties,
                           List<InterceptHandler> interceptHandlers) throws IOException {
        this.mqttServer = mqttServer;
        this.mqttProperties = mqttProperties;
        this.interceptHandlers = interceptHandlers;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            this.mqttServer.startServer(mqttProperties);
            interceptHandlers.forEach(handler -> this.mqttServer.addInterceptHandler(handler));
        } catch (IOException e) {
            throw new MqttMockServerStartupException(e);
        }
    }
}
