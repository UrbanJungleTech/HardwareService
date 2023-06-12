package frentz.daniel.hardwareservice.jsonrpc.model;

public class AverageSensorReadingMessage extends JsonRpcMessage{

    public AverageSensorReadingMessage(long[] sensorPorts){
        super("ReadAverageSensor");
        this.getParams().put("ports", sensorPorts);
    }
}
