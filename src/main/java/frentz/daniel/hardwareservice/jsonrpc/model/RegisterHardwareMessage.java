package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.entity.HardwareEntity;

public class RegisterHardwareMessage extends JsonRpcMessage{
    public RegisterHardwareMessage(HardwareEntity hardware){
        super("RegisterHardware");
        this.getParams().put("port", hardware.getPort());
        this.getParams().put("state", hardware.getDesiredState());
    }
}