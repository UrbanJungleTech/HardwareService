package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.config.mqtt.SystemMqttClientProperties;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.Map;
import java.util.UUID;

@Service
@HardwareControllerCommunicationService(type = "mqtt", custom = false)
public class MqttControllerConfigurationService implements ControllerConfigurationServiceImplementation {

    private Logger logger = LoggerFactory.getLogger(MqttControllerConfigurationService.class);
    private Map<Long, IMqttClient> mqttClients;
    private Map<SystemMqttClientProperties, IMqttClient> serverMqttClients;

    public MqttControllerConfigurationService(@Qualifier("MqttClients") Map<Long, IMqttClient> mqttClients,
                                              @Qualifier("serverMqttClients") Map<SystemMqttClientProperties, IMqttClient> serverMqttClients) {
        this.mqttClients = mqttClients;
        this.serverMqttClients = serverMqttClients;
    }

    @Override
    public void configureController(HardwareController hardwareController){
        try {
            logger.info("Configuring MQTT controller {}", hardwareController.getId());
            String server = hardwareController.getConfiguration().get("server");
            String clientId = UUID.randomUUID().toString();
            IMqttClient client = new MqttClient(server, clientId);
            mqttClients.put(hardwareController.getId(), client);

            String responseQueue = hardwareController.getConfiguration().get("responseQueue");

            SystemMqttClientProperties serverMqttClientProperties = new SystemMqttClientProperties(server, responseQueue);
            if(serverMqttClients.containsKey(serverMqttClientProperties) == false){
                IMqttClient serverClient = new MqttClient(server, UUID.randomUUID().toString());
                serverMqttClients.put(serverMqttClientProperties, serverClient);
                this.logger.info("Created a new server client for {} on queue {}", server, responseQueue);
            }
        }
        catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
