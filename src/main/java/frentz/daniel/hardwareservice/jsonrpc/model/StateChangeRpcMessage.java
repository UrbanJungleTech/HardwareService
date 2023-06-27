package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;

public class StateChangeRpcMessage extends JsonRpcMessage {

    public StateChangeRpcMessage(long port, HardwareState hardwareState) {
        super("StateChange");
        this.getParams().put("desiredState", hardwareState);
        this.getParams().put("port", port);
    }

}
