package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.entity.HardwareStateEntity;

public class StateChangeRpcMessage extends JsonRpcMessage {

    public StateChangeRpcMessage(long port, HardwareStateEntity hardwareState) {
        super("StateChange");
        this.getParams().put("desiredState", hardwareState);
        this.getParams().put("port", port);
    }

}
