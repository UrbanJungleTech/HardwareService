package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.entity.HardwareEntity;

public class DeregisterHardwareMessage extends JsonRpcMessage {
    public DeregisterHardwareMessage(Hardware hardware){
        super("DeregisterHardware");
        this.getParams().put("port", hardware.getPort());
    }
}
