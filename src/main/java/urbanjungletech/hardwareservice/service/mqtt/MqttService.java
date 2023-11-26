package urbanjungletech.hardwareservice.service.mqtt;

import io.reactivex.Observable;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface MqttService {
    void publish(long hardwareControllerId, JsonRpcMessage payload);
    Observable<JsonRpcMessage> publishWithResponse(long hardwareControllerId, JsonRpcMessage payload, long timeout);
}
