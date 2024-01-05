package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

import java.util.Map;

public interface MqttCredentialsConfigurationService {

    void configureCredentials(Long controllerId, MqttConnectOptions options);
}
