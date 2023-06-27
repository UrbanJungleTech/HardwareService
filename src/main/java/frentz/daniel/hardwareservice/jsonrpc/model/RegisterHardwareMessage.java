package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.config.mqtt.mockclient.RegisterHardwareCallback;
import frentz.daniel.hardwareservice.entity.HardwareEntity;

import java.util.Map;

public class RegisterHardwareMessage extends JsonRpcMessage{
    public RegisterHardwareMessage(){
        super("RegisterHardware");
    }
    public RegisterHardwareMessage(Hardware hardware){
        super("RegisterHardware");
        this.getParams().put("port", hardware.getPort());
        this.getParams().put("state", hardware.getDesiredState());
    }
}
