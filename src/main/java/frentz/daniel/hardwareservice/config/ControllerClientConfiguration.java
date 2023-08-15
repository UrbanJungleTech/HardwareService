package frentz.daniel.hardwareservice.config;

import java.util.Map;

public class ControllerClientConfiguration {
    private Map<String, MqttClientConfiguration> mqtt;

    public Map<String, MqttClientConfiguration> getMqtt() {
        return mqtt;
    }

    public void setMqtt(Map<String, MqttClientConfiguration> mqtt) {
        this.mqtt = mqtt;
    }
}
