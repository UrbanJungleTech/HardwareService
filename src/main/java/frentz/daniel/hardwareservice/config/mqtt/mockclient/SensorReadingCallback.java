package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.config.mqtt.SensorReadingException;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SensorReadingCallback implements MockMqttClientCallback {

    private IMqttClient mqttClient;
    private ObjectMapper objectMapper;

    public SensorReadingCallback(IMqttClient mqttClient,
                                 ObjectMapper objectMapper){
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void callback(JsonRpcMessage jsonRpcMessage) {
        try {
            JsonRpcMessage response = new JsonRpcMessage();
            response.setId(jsonRpcMessage.getId());
            Map<String, Object> responsePayload = new HashMap<>();
            responsePayload.put("reading", 1.0);
            response.setResult(responsePayload);
            String messageText = objectMapper.writeValueAsString(response);
            MqttMessage message = new MqttMessage(messageText.getBytes());
            message.setQos(2);
            message.setRetained(false);
            this.mqttClient.publish("FromMicrocontroller", message);
        } catch (Exception e) {
            throw new SensorReadingException(e);
        }
    }
}
