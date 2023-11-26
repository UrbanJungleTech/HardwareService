package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.HardwareState;

public class StateChangeRpcMessage extends JsonRpcMessage {

    public StateChangeRpcMessage(String port, HardwareState hardwareState) {
        super("StateChange");
        this.getParams().put("desiredState", hardwareState);
        this.getParams().put("port", port);
    }

}
