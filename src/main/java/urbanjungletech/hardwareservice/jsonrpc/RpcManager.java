package urbanjungletech.hardwareservice.jsonrpc;

import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface RpcManager {

    void process(JsonRpcMessage jsonRpcMessage);
}
