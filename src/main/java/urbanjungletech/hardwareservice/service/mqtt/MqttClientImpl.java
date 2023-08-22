package urbanjungletech.hardwareservice.service.mqtt;

import urbanjungletech.hardwareservice.config.ControllerConfiguration;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.HardwareControllerService;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttClientImpl implements MqttClient {
    private Map<String, IMqttClient> clients;
    private HardwareControllerService hardwareControllerService;
    private ControllerConfiguration controllerConfiguration;
    public MqttClientImpl(@Qualifier("MqttClients") Map<String, IMqttClient> clients,
                          HardwareControllerService hardwareControllerService,
                          ControllerConfiguration controllerConfiguration) {
        this.clients = clients;
        this.hardwareControllerService = hardwareControllerService;
        this.controllerConfiguration = controllerConfiguration;
    }

    @Override
    public void publish(long hardwareControllerId, MqttMessage message) throws MqttException {
        HardwareController hardwareController = this.hardwareControllerService.getHardwareController(hardwareControllerId);
        String clientName = this.controllerConfiguration.getTypes().get(hardwareController.getType()).getClient();
        this.clients.get(clientName).publish(hardwareController.getSerialNumber() + "ToMicrocontroller", message);
    }

    @Override
    public boolean isConnected() {
        return this.clients.get(0).isConnected();
    }
}
