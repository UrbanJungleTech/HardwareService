package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class MockMqttClientListener implements IMqttMessageListener {
    private ObjectMapper objectMapper;

    private List<JsonRpcMessage> cache;

    private Map<String, List<MockMqttClientCallback>> callbacks;

    Logger logger = LoggerFactory.getLogger(MockMqttClientListener.class);
    public List<JsonRpcMessage> getCache() {
        return cache;
    }

    public List<JsonRpcMessage> getCache(String method, Map<String, Object> params){
        List<JsonRpcMessage> cache = this.getCache(method);
        return cache.stream().filter(message -> {
            for(String key : params.keySet()){
                System.out.println("checking if " + key + " is in " + message.getParams() + " and equals " + params.get(key));

                if(!message.getParams().containsKey(key) || !message.getParams().get(key).equals(params.get(key))){
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public MockMqttClientListener(ObjectMapper objectMapper,
                                  MqttMockClientConfig mqttMockClientConfig,
                                  List<MockMqttClientCallback> mockMqttClientCallbacks){
        this.objectMapper = objectMapper;
        this.callbacks = new HashMap<>();
        this.cache = new CopyOnWriteArrayList<>();
        for(MockMqttClientCallback callback : mockMqttClientCallbacks){
            if(mqttMockClientConfig.getCallbacks().containsKey(callback.getClass().getCanonicalName())){
                String method = mqttMockClientConfig.getCallbacks().get(callback.getClass().getCanonicalName());
                if(!this.callbacks.containsKey(method)){
                    this.callbacks.put(method, new ArrayList<>());
                }
                this.callbacks.get(method).add(callback);
                logger.debug("Added callback for method " + method + " " + callback.getClass().getCanonicalName());
            }
            else{
                logger.debug("No callback for " + callback.getClass().getCanonicalName());
            }
        }
    }
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        logger.info("Got an RPC message -> {}", mqttMessage.getPayload().toString());
        JsonRpcMessage jsonRpcMessage = this.objectMapper.readValue(new String(mqttMessage.getPayload()), JsonRpcMessage.class);
        this.cache.add(jsonRpcMessage);
        List<MockMqttClientCallback> callback = this.callbacks.get(jsonRpcMessage.getMethod());
        if(callback != null){
            for(MockMqttClientCallback mockMqttClientCallback : callback){
                mockMqttClientCallback.callback(jsonRpcMessage);
            }
        }
    }

    public void setCache(List<JsonRpcMessage> cache) {
        this.cache = cache;
    }

    public void clear(){
        this.cache = new CopyOnWriteArrayList <>();
    }

    public List<JsonRpcMessage> getCache(String method){
        return this.cache.stream().filter(jsonRpcMessage -> jsonRpcMessage.getMethod().equals(method)).toList();
    }
}
