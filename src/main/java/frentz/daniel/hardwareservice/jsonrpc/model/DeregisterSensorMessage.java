package frentz.daniel.hardwareservice.jsonrpc.model;

import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.entity.SensorEntity;

public class DeregisterSensorMessage extends JsonRpcMessage{
    public DeregisterSensorMessage(Sensor sensor){
        super("DeregisterSensor");
        this.getParams().put("port", sensor.getPort());
    }
}
