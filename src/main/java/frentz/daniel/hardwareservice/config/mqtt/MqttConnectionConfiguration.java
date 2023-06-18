package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.exception.MqttConnectionException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MqttConnectionConfiguration {

    private AtomicBoolean connecting;
    Logger logger = LoggerFactory.getLogger(MqttConnectionConfiguration.class);
    private IMqttClient mqttClient;
    private List<MqttSubscriptionListener> mqttSubscriptionListeners;
    private MqttConnectOptions mqttConnectOptions;

    public MqttConnectionConfiguration(IMqttClient mqttClient,
                                       List<MqttSubscriptionListener> mqttSubscriptionListeners,
                                       MqttConnectOptions mqttConnectOptions) {
        this.mqttSubscriptionListeners = mqttSubscriptionListeners;
        this.mqttClient = mqttClient;
        connecting = new AtomicBoolean(false);
        this.mqttConnectOptions = mqttConnectOptions;
    }

    @Scheduled(fixedDelay = 1000)
    public void reconnect() {
        try {
            if (mqttClient.isConnected() == false) {
                System.out.println("client " + mqttClient.getClientId());
                IMqttToken token = mqttClient.connectWithResult(mqttConnectOptions);
                for(MqttSubscriptionListener mqttSubscriptionListener : this.mqttSubscriptionListeners ){
                    mqttClient.subscribe(mqttSubscriptionListener.getTopic(), mqttSubscriptionListener.getMqttMessageListener());
                    logger.debug("Successfully registered the mqtt rpc callback {}", mqttSubscriptionListener.getTopic());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }
}
