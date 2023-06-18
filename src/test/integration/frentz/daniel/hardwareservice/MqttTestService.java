package frentz.daniel.hardwareservice;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MqttTestService {
    public void sendMessage(String payload) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", "test");
        client.connect();
        client.publish("FromMicrocontroller", payload.getBytes(), 0, false);
        client.disconnect();
    }
}
