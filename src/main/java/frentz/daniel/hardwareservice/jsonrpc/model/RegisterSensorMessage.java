package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.entity.SensorEntity;

public class RegisterSensorMessage extends JsonRpcMessage{
    public RegisterSensorMessage(Sensor sensor){
        super("RegisterSensor");
        this.getParams().put("port", sensor.getPort());
    }
}
