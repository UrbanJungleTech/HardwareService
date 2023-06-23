package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
public class StateChangeCallback implements MockMqttClientCallback {

    private Map<Long, List<HardwareState>> states;
    Logger logger = LoggerFactory.getLogger(StateChangeCallback.class);
    private ObjectMapper objectMapper;
    public StateChangeCallback(ObjectMapper objectMapper){
        this.states = new HashMap<>();
        this.objectMapper = objectMapper;
    }

    @Override
    public void callback(JsonRpcMessage message) {
        logger.debug("StateChangeCallback called on port {} with hardware state {}", message.getParams().get("port"), message.getParams().get("desiredState"));
        long port = (Integer)message.getParams().get("port");
        HardwareState state = objectMapper.convertValue(message.getParams().get("desiredState"), HardwareState.class);
        if(states.get(port) == null) {
            states.put(port, new ArrayList<>());
        }
        states.get(port).add(state);
    }

    public List<HardwareState> getStates(long port){
        return states.get(port);
    }

    public void clearEvents(){
        this.states.clear();
    }
}
