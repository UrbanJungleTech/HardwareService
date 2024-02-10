package urbanjungletech.hardwareservice.service.client.generator.implementation;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.MqttConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;
import urbanjungletech.hardwareservice.service.client.generator.SpecificClientGenerator;

@Service
public class MqttClientGenerator implements SpecificClientGenerator<MqttClient, MqttConnectionDetails> {
    @Override
    public MqttClient generateClient(MqttConnectionDetails connectionDetails) {
        try {
            MqttClient client = new MqttClient(connectionDetails.getBroker(), connectionDetails.getClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            UsernamePasswordCredentials credentials = (UsernamePasswordCredentials) connectionDetails.getCredentials();
            options.setUserName(credentials.getUsername());
            options.setPassword(credentials.getPassword().toCharArray());
            client.connect(options);
            return client;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to generate MQTT client", e);
        }
    }
}
