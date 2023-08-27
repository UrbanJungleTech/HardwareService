package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.Sensor;

public class RegisterSensorMessage extends JsonRpcMessage{
    public RegisterSensorMessage(Sensor sensor){
        super("RegisterSensor");
        this.getParams().put("port", sensor.getPort());
    }
}
