package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.entity.SensorEntity;

public class RegisterSensorMessage extends JsonRpcMessage{
    public RegisterSensorMessage(SensorEntity sensor){
        super("RegisterSensor");
        this.getParams().put("port", sensor.getPort());
    }
}
