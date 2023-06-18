package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.exception.InvalidMqttClientException;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    private String server;
    private String queue;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }


    @Bean
    public MqttConnectOptions getMqttConnectionOptions(){
        MqttConnectOptions result = new MqttConnectOptions();
        result.setAutomaticReconnect(true);
        result.setCleanSession(false);
        result.setConnectionTimeout(60);
        result.setServerURIs(new String[]{this.getServer()});
        result.setKeepAliveInterval(1800);
        return result;
    }

    @Bean
    public IMqttClient getMqttClient(){
        try {
            IMqttClient result = new MqttClient(this.getServer(), MqttClient.generateClientId());
            return result;
        }
        catch(Exception ex){
            throw new InvalidMqttClientException();
        }
    }

}
