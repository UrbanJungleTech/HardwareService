package urbanjungletech.hardwareservice.jsonrpc;

import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import io.reactivex.Observable;

public interface RpcResponseProcessor {

    void addResponse(JsonRpcMessage message);

    Observable<JsonRpcMessage> awaitResponse(long id, long timeout);
}
