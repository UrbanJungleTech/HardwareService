package frentz.daniel.hardwareservice.config.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public class MqttSubscriptionListener {
    private String topic;
    private IMqttMessageListener mqttMessageListener;

    public MqttSubscriptionListener(String topic, IMqttMessageListener mqttMessageListener) {
        this.topic = topic;
        this.mqttMessageListener = mqttMessageListener;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public IMqttMessageListener getMqttMessageListener() {
        return mqttMessageListener;
    }

    public void setMqttMessageListener(IMqttMessageListener mqttMessageListener) {
        this.mqttMessageListener = mqttMessageListener;
    }
}
