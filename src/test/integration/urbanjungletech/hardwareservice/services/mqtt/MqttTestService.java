package urbanjungletech.hardwareservice.services.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@Profile("test")
public class MqttTestService {
    private Logger logger = Logger.getLogger(MqttTestService.class.getName());
    public void sendMessage(String payload) throws MqttException, InterruptedException {
        logger.info("Sending message: " + payload);
        MqttClient client = new MqttClient("tcp://localhost:1883", UUID.randomUUID().toString(), null);
        client.connect();
        while(client.isConnected() == false){
            Thread.sleep(1000);
        }
        client.publish("HardwareServer", payload.getBytes(), 0, false);
        logger.info("Message sent");
        client.disconnect();
    }
}
