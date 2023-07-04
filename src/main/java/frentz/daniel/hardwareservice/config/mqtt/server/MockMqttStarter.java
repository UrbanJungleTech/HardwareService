package frentz.daniel.hardwareservice.config.mqtt.server;

import frentz.daniel.hardwareservice.config.mqtt.MqttReconnectionJob;
import frentz.daniel.hardwareservice.exception.MqttMockServerStartupException;
import io.moquette.broker.Server;
import io.moquette.interception.InterceptHandler;
import org.eclipse.paho.client.mqttv3.MqttClient;
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
    private MqttReconnectionJob mqttReconnectionJob;

    public MockMqttStarter(Server mqttServer,
                           Properties mqttProperties,
                           List<InterceptHandler> interceptHandlers,
                           MqttReconnectionJob mqttReconnectionJob) throws IOException {
        this.mqttServer = mqttServer;
        this.mqttProperties = mqttProperties;
        this.interceptHandlers = interceptHandlers;
        this.mqttReconnectionJob = mqttReconnectionJob;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        boolean ready = false;
        while (ready == false) {
            try {
                this.mqttServer.startServer(mqttProperties);
                MqttClient client = new MqttClient("tcp://localhost:1883", "test");
                client.connect();
                ready = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
