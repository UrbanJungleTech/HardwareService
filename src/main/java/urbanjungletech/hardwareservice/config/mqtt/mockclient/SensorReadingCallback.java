package urbanjungletech.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.config.mqtt.SensorReadingException;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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

    public SensorReadingCallback(ObjectMapper objectMapper) throws MqttException {
        this.objectMapper = objectMapper;
        this.reading = 1.0;
        this.mqttClient = new MqttClient("tcp://localhost:1883", "sad");
    }

    @Override
    public void callback(JsonRpcMessage jsonRpcMessage) {
        try {
            while(this.mqttClient.isConnected() == false) {
                this.mqttClient.connect();
                Thread.sleep(1000);
            }
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
                    logger.info("Publishing sensor reading: {}", message.getPayload().toString());
                    mqttClient.publish("HardwareServer", message);
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
