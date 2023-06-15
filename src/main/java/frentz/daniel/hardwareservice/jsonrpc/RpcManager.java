package frentz.daniel.hardwareservice.jsonrpc;

import frentz.daniel.hardwareservice.jsonrpc.method.RpcMethod;
import frentz.daniel.hardwareservice.jsonrpc.model.JsonRpcMessage;

public interface RpcManager {

    void process(JsonRpcMessage jsonRpcMessage);
}
