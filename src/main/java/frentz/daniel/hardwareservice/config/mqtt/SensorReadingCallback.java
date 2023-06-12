package frentz.daniel.hardwareservice.config.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;

public class SensorReadingCallback implements MockMqttClientCallback{

    private IMqttClient mqttClient;
    private ObjectMapper objectMapper;

    public SensorReadingCallback(IMqttClient mqttClient,
                                 ObjectMapper objectMapper){
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getName() {
        return "ReadSensor";
    }

    @Override
    public void callback(JsonRpcMessage jsonRpcMessage) {
        try {
            System.out.println("Got a sensor reading: " + jsonRpcMessage.getResult());
            JsonRpcMessage response = new JsonRpcMessage();
            response.setId(jsonRpcMessage.getId());
            Map<String, Object> responsePayload = new HashMap<>();
            responsePayload.put("reading", 1.0);
            response.setResult(responsePayload);
            String messageText = objectMapper.writeValueAsString(response);
            MqttMessage message = new MqttMessage(messageText.getBytes());
            message.setQos(2);
            message.setRetained(false);
            System.out.println("sending message from mock");
            this.mqttClient.publish("FromMicrocontroller", message);
        } catch (Exception e) {
            throw new SensorReadingException(e);
        }
    }
}
