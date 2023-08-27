package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.Hardware;

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
