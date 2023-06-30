package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.config.mqtt.SensorReadingException;
import frentz.daniel.hardwareservice.controller.HardwareControllerEndpoint;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SensorReadingCallback implements MockMqttClientCallback {
    private final Logger logger = LoggerFactory.getLogger(SensorReadingCallback.class);
    private IMqttClient mqttClient;
    private ObjectMapper objectMapper;
    private double reading;

    public SensorReadingCallback(IMqttClient mqttClient,
                                 ObjectMapper objectMapper){
        this.mqttClient = mqttClient;
        this.objectMapper = objectMapper;
        this.reading = 1.0;
    }

    @Override
    public void callback(JsonRpcMessage jsonRpcMessage) {
        try {
            this.logger.info("Received sensor read message: {}", jsonRpcMessage);
            JsonRpcMessage response = new JsonRpcMessage();
            response.setId(jsonRpcMessage.getId());
            Map<String, Object> responsePayload = new HashMap<>();
            responsePayload.put("reading", this.reading);
            response.setResult(responsePayload);
            String messageText = objectMapper.writeValueAsString(response);
            MqttMessage message = new MqttMessage(messageText.getBytes());
            message.setQos(2);
            message.setRetained(false);
            new Thread(() -> {
                try {
                    mqttClient.publish("FromMicrocontroller", message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            throw new SensorReadingException(e);
        }
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }
}
