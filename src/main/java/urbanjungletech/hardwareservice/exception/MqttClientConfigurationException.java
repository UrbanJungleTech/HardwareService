package urbanjungletech.hardwareservice.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientConfigurationException extends RuntimeException {
    public MqttClientConfigurationException(MqttException e) {
    }
}
