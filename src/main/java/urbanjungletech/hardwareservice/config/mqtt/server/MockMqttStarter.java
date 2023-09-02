package urbanjungletech.hardwareservice.config.mqtt.server;

import io.moquette.broker.Server;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Properties;

@Configuration
@ConditionalOnProperty(value = "local.mqtt.enabled", havingValue = "true")
public class MockMqttStarter implements ApplicationListener<ContextRefreshedEvent> {
    private Server mqttServer;
    private Properties mqttProperties;
    private Logger logger = LoggerFactory.getLogger(MockMqttStarter.class);

    public MockMqttStarter(Server mqttServer,
                           Properties mqttProperties) throws IOException {
        this.mqttServer = mqttServer;
        this.mqttProperties = mqttProperties;
        this.mqttProperties.setProperty("persistence_enabled", "false");
        this.mqttProperties.setProperty("telemetry_enabled", "false");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        boolean ready = false;
            try {
                this.mqttServer.stopServer();
                this.mqttServer.startServer(mqttProperties);
                while (ready == false) {
                    MqttClient client = new MqttClient("tcp://localhost:1883", "test");
                    client.connect();
                    logger.info("Mock MQTT server started");
                    ready = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
