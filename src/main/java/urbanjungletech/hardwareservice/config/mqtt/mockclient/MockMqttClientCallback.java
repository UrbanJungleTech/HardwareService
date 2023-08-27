package urbanjungletech.hardwareservice.config.mqtt.mockclient;

import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.springframework.stereotype.Service;

@Service
public interface MockMqttClientCallback {
    public void callback(JsonRpcMessage message);
}
