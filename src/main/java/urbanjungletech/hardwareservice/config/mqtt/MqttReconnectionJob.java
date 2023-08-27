package urbanjungletech.hardwareservice.config.mqtt;

import urbanjungletech.hardwareservice.config.ControllerConfiguration;
import urbanjungletech.hardwareservice.config.ListenerConfiguration;
import urbanjungletech.hardwareservice.exception.exception.MqttConnectionException;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;
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
    private Logger logger = LoggerFactory.getLogger(MqttReconnectionJob.class);
    private Map<Long, IMqttClient> clients;
    private Map<String, IMqttMessageListener> listeners;
    private IMqttClient serverClient;
    IMqttMessageListener microcontrollerMessageListener;
    public MqttReconnectionJob(@Qualifier("MqttClients") Map<Long, IMqttClient> clients,
                               Map<String, IMqttMessageListener> listeners,
                               @Qualifier("serverClient") IMqttClient serverClient,
                               @Qualifier("microcontrollerMessageListener") IMqttMessageListener microcontrollerMessageListener) {
        this.clients = clients;
        this.listeners = listeners;
        this.serverClient = serverClient;
        this.microcontrollerMessageListener = microcontrollerMessageListener;
    }

    @Scheduled(fixedDelay = 100)
    public void reconnect() {
        try {
            for (Long mqttClient : this.clients.keySet()) {
                IMqttClient client = this.clients.get(mqttClient);
                if (client.isConnected() == false) {
                    client.connect();
                    for (IMqttMessageListener listener : this.listeners.values()) {
                        client.subscribe("1234ToMicrocontroller", listener);
//                        client.subscribe("HardwareServer", listener);
                        logger.debug("Successfully registered the mqtt rpc callback on topic {} with nane {}", "1234ToMicrocontroller", listener.getClass().getSimpleName());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }

    @Scheduled(fixedDelay = 100)
    public void reconnectServerClient() {
        try {
            if(this.serverClient.isConnected() == false){
                this.serverClient.connect();
                this.serverClient.subscribe("HardwareServer", microcontrollerMessageListener);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }
}
