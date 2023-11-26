package urbanjungletech.hardwareservice.config.mqtt.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.jsonrpc.RpcManager;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

@Service
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
        logger.info("Got an RPC message on queue -> {}", payload);
        JsonRpcMessage jsonRpcMessage = objectMapper.readValue(payload, JsonRpcMessage.class);
        rpcManager.process(jsonRpcMessage);
    }
}
