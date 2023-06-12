package frentz.daniel.hardwareservice.jsonrpc.model;

import java.util.List;

public class InitialStateRpcMessage extends JsonRpcMessage {

    public InitialStateRpcMessage(List<HardwareStateRpcMessage> states){
        super("InitialState");
        this.getParams().put("hardwareStates", states);
    }
}
