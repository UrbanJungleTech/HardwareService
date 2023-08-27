package urbanjungletech.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.config.mqtt.listener.MicrocontrollerMessageListener;

@Configuration
public class SystemClient {
    @Bean
    public IMqttClient serverClient(MicrocontrollerMessageListener microcontrollerMessageListener) throws MqttException {
        IMqttClient result = new MqttClient("tcp://localhost:1883", "HardwareServer");
        return result;
    }
}
