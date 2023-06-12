package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.exception.MqttConnectionException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MqttSubscriptionConfiguration {

    private AtomicBoolean connecting;
    Logger logger = LoggerFactory.getLogger(MqttSubscriptionConfiguration.class);
    private IMqttClient mqttClient;
    private List<MqttSubscriptionListener> mqttSubscriptionListeners;

    public MqttSubscriptionConfiguration(IMqttClient mqttClient,
                                         List<MqttSubscriptionListener> mqttSubscriptionListeners) {
        this.mqttSubscriptionListeners = mqttSubscriptionListeners;
        this.mqttClient = mqttClient;
        connecting = new AtomicBoolean(false);
    }

    @Scheduled(fixedRate = 1000)
    public void reconnect() {
        try {
            if (!connecting.getAndSet(true) && mqttClient.isConnected() == false) {
                mqttClient.connect();
                for(MqttSubscriptionListener mqttSubscriptionListener : this.mqttSubscriptionListeners ){
                    mqttClient.subscribe(mqttSubscriptionListener.getTopic(), mqttSubscriptionListener.getMqttMessageListener());
                    logger.debug("Successfully registered the mqtt rpc callback {}", mqttSubscriptionListener.getTopic());
                }
            }
        } catch (Exception ex) {
            throw new MqttConnectionException();
        }
        finally{
            connecting.set(false);
        }
    }
}
