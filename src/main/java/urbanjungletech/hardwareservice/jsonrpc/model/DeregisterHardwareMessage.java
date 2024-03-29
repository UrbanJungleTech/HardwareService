package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.hardware.Hardware;

public class DeregisterHardwareMessage extends JsonRpcMessage {
    public DeregisterHardwareMessage(Hardware hardware){
        super("DeregisterHardware");
        this.getParams().put("port", hardware.getPort());
    }
}
