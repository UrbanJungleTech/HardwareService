package urbanjungletech.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.exception.exception.MqttConnectionException;

import java.util.Map;

@Service
public class MqttReconnectionJob {
    private final Logger logger = LoggerFactory.getLogger(MqttReconnectionJob.class);
    private final Map<Long, IMqttClient> clients;
    private final Map<SystemMqttClientProperties, IMqttClient> serverClients;
    private final IMqttMessageListener microcontrollerMessageListener;
    public MqttReconnectionJob(@Qualifier("MqttClients") Map<Long, IMqttClient> clients,
                               @Qualifier("serverMqttClients") Map<SystemMqttClientProperties, IMqttClient> serverClients,
                               @Qualifier("microcontrollerMessageListener") IMqttMessageListener microcontrollerMessageListener) {
        this.clients = clients;
        this.serverClients = serverClients;
        this.microcontrollerMessageListener = microcontrollerMessageListener;
    }

@Transactional
    @Scheduled(fixedDelay = 100)
    public void reconnect() {
        try {
            for (Long mqttClientHardwareControllerId : this.clients.keySet()) {
                IMqttClient client = this.clients.get(mqttClientHardwareControllerId);
                if (client.isConnected() == false) {
                    client.connect();
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
            for(SystemMqttClientProperties key : this.serverClients.keySet()){
                IMqttClient client = this.serverClients.get(key);
                if(client.isConnected() == false){
                    client.connect();
                    client.subscribe(key.getQueue(), microcontrollerMessageListener);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }
}
