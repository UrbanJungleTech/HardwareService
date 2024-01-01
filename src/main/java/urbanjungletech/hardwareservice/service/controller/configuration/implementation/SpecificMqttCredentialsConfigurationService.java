package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

public interface SpecificMqttCredentialsConfigurationService<CredentialsType extends Credentials> {
    void configureCredentials(MqttHardwareController hardwareController, MqttConnectOptions options);
}
