package urbanjungletech.hardwareservice.services.mqtt;

import io.moquette.broker.Server;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import urbanjungletech.hardwareservice.services.mqtt.mockclient.MockMqttClientListener;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@Configuration
//@ConditionalOnProperty(value = "mqtt.mock.enabled", havingValue = "true")
public class MockMqttStarter implements ApplicationListener<ContextRefreshedEvent> {
    private Server mqttServer;
    private Properties mqttProperties;
    private Logger logger = LoggerFactory.getLogger(MockMqttStarter.class);
    private MockMqttClientListener mockMqttClientListener;

    public MockMqttStarter(Server mqttServer,
                           Properties mqttProperties,
                           MockMqttClientListener mockMqttClientListener) throws IOException {
        this.mqttServer = mqttServer;
        this.mqttProperties = mqttProperties;
        this.mqttProperties.setProperty("persistence_enabled", "false");
        this.mqttProperties.setProperty("telemetry_enabled", "false");
        this.mockMqttClientListener = mockMqttClientListener;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        boolean ready = false;
            try {
                this.mqttServer.stopServer();
                this.mqttServer.startServer(mqttProperties);

                MqttClient client = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString(), null);
                while (ready == false) {
                    client.connect();
                    logger.info("Mock MQTT server started");
                    ready = true;
                }
                client.subscribe("1234ToMicro", mockMqttClientListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
