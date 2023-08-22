package urbanjungletech.hardwareservice.service.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttClient {
    void publish(long hardwareControllerId, MqttMessage message) throws MqttException;
    boolean isConnected();
}
