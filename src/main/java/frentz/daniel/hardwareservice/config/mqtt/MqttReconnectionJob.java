package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.config.ControllerConfiguration;
import frentz.daniel.hardwareservice.config.ListenerConfiguration;
import frentz.daniel.hardwareservice.exception.MqttConnectionException;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MqttReconnectionJob {

    private AtomicBoolean connecting;
    private Logger logger = LoggerFactory.getLogger(MqttReconnectionJob.class);
    private Map<String, IMqttClient> clients;
    private Map<String, IMqttMessageListener> listeners;
    private ControllerConfiguration controllerConfiguration;
    private HardwareControllerService hardwareControllerService;

    public MqttReconnectionJob(@Qualifier("MqttClients") Map<String, IMqttClient> clients,
                               Map<String, IMqttMessageListener> listeners,
                               ControllerConfiguration controllerConfiguration) {
        this.clients = clients;
        this.listeners = listeners;
        this.controllerConfiguration = controllerConfiguration;
    }

    @Scheduled(fixedDelay = 100)
    public void reconnect() {
        try {
            for (String mqttClient : this.controllerConfiguration.getClients().getMqtt().keySet()) {
                IMqttClient client = this.clients.get(mqttClient);
                if (client.isConnected() == false) {
                    client.connect();
                    logger.info("Registering listeners {} ", controllerConfiguration.getClients().getMqtt().get(mqttClient).getListeners());
                    for (ListenerConfiguration listenerConfiguration : controllerConfiguration.getClients().getMqtt().get(mqttClient).getListeners()) {
                        IMqttMessageListener listener = this.listeners.get(listenerConfiguration.getName());
                        client.subscribe(listenerConfiguration.getQueue(), listener);
                        logger.debug("Successfully registered the mqtt rpc callback on topic {} with nane {}", listenerConfiguration.getQueue(), listenerConfiguration.getName());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }
}
