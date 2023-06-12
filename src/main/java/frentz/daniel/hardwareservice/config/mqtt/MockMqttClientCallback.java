package frentz.daniel.hardwareservice.config.mqtt;

import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface MockMqttClientCallback {
    public String getName();
    public void callback(JsonRpcMessage message);
}
