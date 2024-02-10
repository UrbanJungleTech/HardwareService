package urbanjungletech.hardwareservice.jsonrpc;

import io.reactivex.rxjava3.core.Observable;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface RpcResponseProcessor {

    void addResponse(JsonRpcMessage message);

    Observable<JsonRpcMessage> awaitResponse(long id, long timeout);
}
