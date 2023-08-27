package urbanjungletech.hardwareservice.config.mqtt.listener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public class MqttSubscriptionListener {
    private String topic;
    private IMqttMessageListener listener;

    public MqttSubscriptionListener(String topic, IMqttMessageListener listener) {
        this.topic = topic;
        this.listener = listener;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public IMqttMessageListener getListener() {
        return listener;
    }

    public void setListener(IMqttMessageListener listener) {
        this.listener = listener;
    }
}
