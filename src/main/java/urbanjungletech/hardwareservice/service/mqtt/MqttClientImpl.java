package urbanjungletech.hardwareservice.service.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
public class MqttClientImpl implements MqttClient {
    private final Map<Long, IMqttClient> clients;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    public MqttClientImpl(@Qualifier("MqttClients") Map<Long, IMqttClient> clients,
                          HardwareControllerQueryService hardwareControllerQueryService) {
        this.clients = clients;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @Override
    public void publish(long hardwareControllerId, MqttMessage message) throws MqttException {
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(hardwareControllerId);
        this.clients.get(hardwareControllerId).publish(hardwareController.getConfiguration().get("serialNumber") + "ToMicrocontroller", message);
    }

    @Override
    public boolean isConnected() {
        return this.clients.get(0).isConnected();
    }
}
