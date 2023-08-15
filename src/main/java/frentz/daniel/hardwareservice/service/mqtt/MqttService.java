package frentz.daniel.hardwareservice.service.mqtt;

import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import io.reactivex.Observable;

public interface MqttService {
    void publish(String serialNumber, JsonRpcMessage payload);
    Observable<JsonRpcMessage> publishWithResponse(String serialNumber, JsonRpcMessage payload, long timeout);
}
