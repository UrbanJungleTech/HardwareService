package urbanjungletech.hardwareservice;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MqttTestService {
    public void sendMessage(String payload) throws MqttException, InterruptedException {
        MqttClient client = new MqttClient("tcp://localhost:1883", "test", null);
        client.connect();
        while(client.isConnected() == false){
            System.out.println("Waiting for connection");
            Thread.sleep(1000);
        }
        client.publish("HardwareServer", payload.getBytes(), 0, false);
        client.disconnect();
    }
}
