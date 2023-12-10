package urbanjungletech.hardwareservice.services.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Service
@ConditionalOnProperty(value = "development.mqtt.client.enabled", havingValue = "true")
public class MqttTestService {
    private Logger logger = Logger.getLogger(MqttTestService.class.getName());
    public void sendMessage(String payload) throws MqttException, InterruptedException {
        logger.info("Sending message: " + payload);
        MqttClient client = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString(), null);
        client.connect();
        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertTrue(client.isConnected());
                });
        client.publish("HardwareServer", payload.getBytes(), 0, false);
        logger.info("Message sent");
        client.disconnect();
    }
}
