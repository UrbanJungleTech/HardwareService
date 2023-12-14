package urbanjungletech.hardwareservice.helpers.services.mqtt.mockclient;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

@Service
public interface MockMqttClientCallback {
    public void callback(JsonRpcMessage message);
}
