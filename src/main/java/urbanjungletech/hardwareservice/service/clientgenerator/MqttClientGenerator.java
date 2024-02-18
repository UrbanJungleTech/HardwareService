package urbanjungletech.hardwareservice.service.clientgenerator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import urbanjungletech.hardwareservice.model.connectiondetails.MqttConnectionDetails;

public interface MqttClientGenerator {
    MqttClient generateClient(MqttConnectionDetails connectionDetails);
}
