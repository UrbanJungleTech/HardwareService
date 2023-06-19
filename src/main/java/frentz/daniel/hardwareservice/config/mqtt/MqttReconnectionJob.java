package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.config.mqtt.listener.MqttSubscriptionListener;
import frentz.daniel.hardwareservice.exception.MqttConnectionException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MqttReconnectionJob {

    private AtomicBoolean connecting;
    Logger logger = LoggerFactory.getLogger(MqttReconnectionJob.class);
    private IMqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    private MqttConfig mqttConfig;
    private List<MqttSubscriptionListener> mqttSubscriptionListeners;

    public MqttReconnectionJob(IMqttClient mqttClient,
                               List<IMqttMessageListener> mqttMessageListeners,
                               MqttConnectOptions mqttConnectOptions,
                               MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
        this.mqttClient = mqttClient;
        connecting = new AtomicBoolean(false);
        this.mqttConnectOptions = mqttConnectOptions;
        this.mqttSubscriptionListeners = new java.util.ArrayList<>();
        for(IMqttMessageListener listener : mqttMessageListeners){
            String topic = this.mqttConfig.getListeners().get(listener.getClass().getCanonicalName());
            if(topic != null) {
                this.mqttSubscriptionListeners.add(new MqttSubscriptionListener(topic, listener));
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void reconnect() {
        try {
            if (mqttClient.isConnected() == false) {
                System.out.println("client " + mqttClient.getClientId());
                IMqttToken token = mqttClient.connectWithResult(mqttConnectOptions);
                for(MqttSubscriptionListener mqttSubscriptionListener : this.mqttSubscriptionListeners){
                    mqttClient.subscribe(mqttSubscriptionListener.getTopic(), mqttSubscriptionListener.getListener());
                    logger.debug("Successfully registered the mqtt rpc callback {}", mqttSubscriptionListener.getTopic());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MqttConnectionException();
        }
    }
}
