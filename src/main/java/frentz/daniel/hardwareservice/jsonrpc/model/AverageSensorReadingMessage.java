package frentz.daniel.hardwareservice.jsonrpc.model;

import java.util.Map;

public class AverageSensorReadingMessage extends JsonRpcMessage{

    public AverageSensorReadingMessage(long[] sensorPorts){
        super("ReadAverageSensor", Map.of("ports", sensorPorts));
    }
}
