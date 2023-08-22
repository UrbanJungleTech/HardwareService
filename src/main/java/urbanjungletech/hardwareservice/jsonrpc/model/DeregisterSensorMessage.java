package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.Sensor;

public class DeregisterSensorMessage extends JsonRpcMessage{
    public DeregisterSensorMessage(Sensor sensor){
        super("DeregisterSensor");
        this.getParams().put("port", sensor.getPort());
    }
}
