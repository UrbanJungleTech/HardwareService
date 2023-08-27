package urbanjungletech.hardwareservice.service.controller.configuration;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.Map;

@Service
@HardwareControllerCommunicationService(type = "mqtt", custom = false)
public class MqttControllerConfigurationService implements ControllerConfigurationServiceImplementation {

    private Map<Long, IMqttClient> mqttClients;

    public MqttControllerConfigurationService(@Qualifier("MqttClients") Map<Long, IMqttClient> mqttClients) {
        this.mqttClients = mqttClients;
    }

    @Override
    public void configureController(HardwareController hardwareController){
        try {
            String server = hardwareController.getConfiguration().get("server");
            String clientId = hardwareController.getConfiguration().get("clientId");
            IMqttClient client = new MqttClient(server, clientId);
            mqttClients.put(hardwareController.getId(), client);
        }
        catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
