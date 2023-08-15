package frentz.daniel.hardwareservice.service.mqtt;

import frentz.daniel.hardwareservice.config.ControllerConfiguration;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import frentz.daniel.hardwareservice.service.mqtt.MqttClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttClientImpl implements MqttClient {
    private Map<String, IMqttClient> clients;
    private HardwareControllerService hardwareControllerService;
    private ControllerConfiguration controllerConfiguration;
    public MqttClientImpl(Map<String, IMqttClient> clients,
                          HardwareControllerService hardwareControllerService,
                          ControllerConfiguration controllerConfiguration) {
        this.clients = clients;
        this.hardwareControllerService = hardwareControllerService;
        this.controllerConfiguration = controllerConfiguration;
    }

    @Override
    public void publish(String serialNumber, MqttMessage message) throws MqttException {
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        String clientName = this.controllerConfiguration.getTypes().get(controllerType).getClient();
        this.clients.get(clientName).publish(serialNumber + "ToMicrocontroller", message);
    }

    @Override
    public boolean isConnected() {
        return this.clients.get(0).isConnected();
    }
}
