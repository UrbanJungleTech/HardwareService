package urbanjungletech.hardwareservice.jsonrpc.model;

import java.util.Map;

public class AverageSensorReadingMessage extends JsonRpcMessage{

    public AverageSensorReadingMessage(String[] sensorPorts){
        super("ReadAverageSensor", Map.of("ports", sensorPorts));
    }
}
