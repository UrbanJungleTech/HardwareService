package frentz.daniel.hardwareservice.jsonrpc.model;

import java.util.Map;

public class SensorPortReadingMessage extends JsonRpcMessage{
    public SensorPortReadingMessage(long port){
        super("ReadSensor", Map.of("port", port));
    }
}
