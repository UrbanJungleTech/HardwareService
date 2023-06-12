package frentz.daniel.hardwareservice.config.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.jsonrpc.RpcManager;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("MicrocontrollerMqttListener")
public class MicrocontrollerMessageListener implements IMqttMessageListener {
    private RpcManager rpcManager;
    private ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(MicrocontrollerMessageListener.class);

    public MicrocontrollerMessageListener(RpcManager rpcManager, ObjectMapper objectMapper) {
        this.rpcManager = rpcManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String payload = new String(mqttMessage.getPayload());
        logger.info("Got an RPC message -> {}", payload);
        JsonRpcMessage jsonRpcMessage = objectMapper.readValue(payload, JsonRpcMessage.class);
        rpcManager.process(jsonRpcMessage);
    }
}
