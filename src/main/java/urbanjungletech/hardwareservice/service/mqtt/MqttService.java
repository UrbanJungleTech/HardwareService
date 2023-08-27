package urbanjungletech.hardwareservice.service.mqtt;

import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import io.reactivex.Observable;

public interface MqttService {
    void publish(long hardwareControllerId, JsonRpcMessage payload);
    Observable<JsonRpcMessage> publishWithResponse(long hardwareControllerId, JsonRpcMessage payload, long timeout);
}
