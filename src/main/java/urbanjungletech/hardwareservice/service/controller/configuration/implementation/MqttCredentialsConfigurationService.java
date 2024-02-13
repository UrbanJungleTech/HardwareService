package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public interface MqttCredentialsConfigurationService {

    void configureCredentials(Long controllerId, MqttConnectOptions options);
}
