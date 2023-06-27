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

@Service
public class MockMqttClientListener implements IMqttMessageListener {
    private ObjectMapper objectMapper;

    public List<JsonRpcMessage> getCache() {
        return cache;
    }

    public List<JsonRpcMessage> getCache(String method, Map<String, Object> params){
        List<JsonRpcMessage> cache = this.getCache(method);
        cache.stream().filter(message -> {
            for(String key : params.keySet()){
                if(!message.getParams().containsKey(key) || !message.getParams().get(key).equals(params.get(key))){
                    return false;
                }
            }
            return true;
        });
        return cache;
    }

    private List<JsonRpcMessage> cache;

    private Map<String, List<MockMqttClientCallback>> callbacks;

    Logger logger = LoggerFactory.getLogger(MockMqttClientListener.class);

    public MockMqttClientListener(ObjectMapper objectMapper,
                                  MqttMockClientConfig mqttMockClientConfig,
                                  List<MockMqttClientCallback> callbacks){
        this.objectMapper = objectMapper;
        this.callbacks = new HashMap<>();
        this.cache = new CopyOnWriteArrayList<>();
        for(MockMqttClientCallback callback : callbacks){
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
