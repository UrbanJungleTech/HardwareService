package frentz.daniel.hardwareservice.jsonrpc.model;

public class SensorPortReadingMessage extends JsonRpcMessage{
    public SensorPortReadingMessage(long port){
        super("ReadSensor");
        this.getParams().put("port", port);
    }
}
