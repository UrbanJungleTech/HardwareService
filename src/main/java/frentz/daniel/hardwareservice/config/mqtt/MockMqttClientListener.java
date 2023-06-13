package frentz.daniel.hardwareservice.config.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("MockClientMqttListener")
public class MockMqttClientListener implements IMqttMessageListener {
    private ObjectMapper objectMapper;
    private MqttMockClientConfig mqttMockClientConfig;
    private IMqttClient mqttClient;

    private Map<String, MockMqttClientCallback> callbacks;

    public MockMqttClientListener(ObjectMapper objectMapper,
                                  MqttMockClientConfig mqttMockClientConfig,
                                  IMqttClient mqttClient,
                                  List<MockMqttClientCallback> callbacks){
        this.objectMapper = objectMapper;
        this.mqttMockClientConfig = mqttMockClientConfig;
        this.mqttClient = mqttClient;
        this.callbacks = new HashMap<>();
        for(MockMqttClientCallback callback : callbacks){
            this.callbacks.put(callback.getName(), callback);
        }
    }
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        JsonRpcMessage jsonRpcMessage = this.objectMapper.readValue(new String(mqttMessage.getPayload()), JsonRpcMessage.class);
        System.out.println("this is mock, Got a message: " + new String(mqttMessage.getPayload()));
        System.out.println("calling " + jsonRpcMessage.getMethod() + " callback");
        MockMqttClientCallback callback = this.callbacks.get(jsonRpcMessage.getMethod());
        if(callback != null){
            callback.callback(jsonRpcMessage);
        }
    }
}
