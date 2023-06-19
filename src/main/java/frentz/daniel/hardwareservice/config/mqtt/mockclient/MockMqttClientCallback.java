package frentz.daniel.hardwareservice.config.mqtt.mockclient;

import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.springframework.stereotype.Service;

@Service
public interface MockMqttClientCallback {
    public void callback(JsonRpcMessage message);
}
