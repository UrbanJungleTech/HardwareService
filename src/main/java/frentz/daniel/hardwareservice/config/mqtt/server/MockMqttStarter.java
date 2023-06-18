package frentz.daniel.hardwareservice.config.mqtt.server;

import frentz.daniel.hardwareservice.exception.MqttMockServerStartupException;
import io.moquette.broker.Server;
import io.moquette.interception.InterceptHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.BindException;
import java.util.List;
import java.util.Properties;

@Configuration
@ConditionalOnProperty(value = "local.mqtt.enabled", havingValue = "true")
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
        } catch (Exception e) {
            System.out.println("error starting server");
            e.printStackTrace();
        }
    }
}
