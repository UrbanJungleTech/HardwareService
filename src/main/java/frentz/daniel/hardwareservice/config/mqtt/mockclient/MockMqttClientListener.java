package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.config.mqtt.mockclient.MockMqttClientCallback;
import frentz.daniel.hardwareservice.config.mqtt.mockclient.MqttMockClientConfig;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MockMqttClientListener implements IMqttMessageListener {
    private ObjectMapper objectMapper;
    private MqttMockClientConfig mqttMockClientConfig;
    private IMqttClient mqttClient;

    private Map<String, List<MockMqttClientCallback>> callbacks;

    public MockMqttClientListener(ObjectMapper objectMapper,
                                  MqttMockClientConfig mqttMockClientConfig,
                                  IMqttClient mqttClient,
                                  List<MockMqttClientCallback> callbacks){
        this.objectMapper = objectMapper;
        this.mqttMockClientConfig = mqttMockClientConfig;
        this.mqttClient = mqttClient;
        this.callbacks = new HashMap<>();
        for(MockMqttClientCallback callback : callbacks){
            if(mqttMockClientConfig.getCallbacks().containsKey(callback.getClass().getCanonicalName())){
                String method = mqttMockClientConfig.getCallbacks().get(callback.getClass().getCanonicalName());
                if(!this.callbacks.containsKey(method)){
                    this.callbacks.put(method, new ArrayList<>());
                }
                this.callbacks.get(method).add(callback);
                System.out.println("Added callback for method " + method + " " + callback.getClass().getCanonicalName());
            }
        }
    }
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        JsonRpcMessage jsonRpcMessage = this.objectMapper.readValue(new String(mqttMessage.getPayload()), JsonRpcMessage.class);
        List<MockMqttClientCallback> callback = this.callbacks.get(jsonRpcMessage.getMethod());
        if(callback != null){
            for(MockMqttClientCallback mockMqttClientCallback : callback){
                mockMqttClientCallback.callback(jsonRpcMessage);
            }
        }
    }
}
