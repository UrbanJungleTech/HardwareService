package frentz.daniel.hardwareservice.config.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttListenerConfiguration {
    @Bean
    public MqttSubscriptionListener mqttServerSubscriptionListener(@Qualifier("MicrocontrollerMqttListener") IMqttMessageListener mqttMessageListener){
        return new MqttSubscriptionListener("FromMicrocontroller", mqttMessageListener);
    }

    @Bean
    @ConditionalOnProperty(prefix = "development.mqtt.client", name = "enabled", havingValue = "true")
    public MqttSubscriptionListener mqttMockClientSubscriptionListener(@Qualifier("MockClientMqttListener") IMqttMessageListener mqttMessageListener, MqttMockClientConfig mqttMockClientConfig){
        return new MqttSubscriptionListener(mqttMockClientConfig.getSerialNumber() + "ToMicrocontroller", mqttMessageListener);
    }

    @Bean
    @ConditionalOnProperty(prefix = "development.mqtt.client", name = "enabled", havingValue = "true")
    public MockMqttClientCallback readSensorMockCallback(IMqttClient mqttClient, ObjectMapper objectMapper){
        return new SensorReadingCallback(mqttClient, objectMapper);
    }

}
