package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.config.mqtt.SystemMqttClientProperties;
import urbanjungletech.hardwareservice.exception.MqttClientConfigurationException;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationService;

import java.util.Map;
import java.util.UUID;

@Service
public class MqttControllerConfigurationService implements ControllerConfigurationService<MqttHardwareController> {

    private final Logger logger = LoggerFactory.getLogger(MqttControllerConfigurationService.class);
    private final Map<Long, IMqttClient> mqttClients;
    private final Map<SystemMqttClientProperties, IMqttClient> serverMqttClients;
    private final MqttCredentialsConfigurationService mqttCredentialsConfigurationService;


    public MqttControllerConfigurationService(@Qualifier("MqttClients") Map<Long, IMqttClient> mqttClients,
                                              @Qualifier("serverMqttClients") Map<SystemMqttClientProperties, IMqttClient> serverMqttClients,
                                              MqttCredentialsConfigurationService mqttCredentialsConfigurationService) {
        this.mqttClients = mqttClients;
        this.serverMqttClients = serverMqttClients;
        this.mqttCredentialsConfigurationService = mqttCredentialsConfigurationService;
    }

    @Override
    public void configureController(MqttHardwareController hardwareController) {
        try {
            //setup the client to send messages to the request queue.
            logger.info("Configuring MQTT controller {}", hardwareController.getId());
            String server = hardwareController.getHardwareMqttClient().getBrokerUrl();
            String clientId = UUID.randomUUID().toString();
            IMqttClient client = new MqttClient(server, clientId);
            mqttClients.put(hardwareController.getId(), client);

            //setup the client to listen to the response queue if one doesn't already exist.
            String responseQueue = hardwareController.getHardwareMqttClient().getResponseTopic();
            SystemMqttClientProperties serverMqttClientProperties = new SystemMqttClientProperties(server, responseQueue);

            if(serverMqttClients.containsKey(serverMqttClientProperties) == false){
                IMqttClient serverClient = new MqttClient(server, UUID.randomUUID().toString());
                serverMqttClients.put(serverMqttClientProperties, serverClient);
                this.logger.info("Created a new server client for {} on queue {}", server, responseQueue);
            }
        }
        catch (MqttException e) {
            throw new MqttClientConfigurationException(e);
        }
    }
}
