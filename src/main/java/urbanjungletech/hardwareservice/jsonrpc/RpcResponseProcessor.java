package urbanjungletech.hardwareservice.jsonrpc;

import io.reactivex.Observable;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface RpcResponseProcessor {

    void addResponse(JsonRpcMessage message);

    Observable<JsonRpcMessage> awaitResponse(long id, long timeout);
}
