package urbanjungletech.hardwareservice.jsonrpc.model;

import java.util.Map;

public class SensorPortReadingMessage extends JsonRpcMessage{
    public SensorPortReadingMessage(String port){
        super("ReadSensor", Map.of("port", port));
    }
}
