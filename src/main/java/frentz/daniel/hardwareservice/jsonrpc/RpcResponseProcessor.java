package frentz.daniel.hardwareservice.jsonrpc;

import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;
import io.reactivex.Observable;

public interface RpcResponseProcessor {

    void addResponse(JsonRpcMessage message);

    Observable<JsonRpcMessage> awaitResponse(long id, long timeout);
}
