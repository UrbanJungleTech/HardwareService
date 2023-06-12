package frentz.daniel.hardwareservice.jsonrpc.method;

import frentz.daniel.hardwareservice.addition.SensorAdditionService;
import frentz.daniel.hardwareservice.service.SensorService;
import frentz.daniel.hardwareservice.client.model.Sensor;

import java.util.Map;

public class DeregisterSensor implements RpcMethod{

    private SensorService sensorService;
    private SensorAdditionService sensorAdditionService;

    public DeregisterSensor(SensorService sensorService,
                            SensorAdditionService sensorAdditionService){
        this.sensorService = sensorService;
        this.sensorAdditionService = sensorAdditionService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");
        int hardwarePort = (int)params.get("sensorPort");
        Sensor sensor = this.sensorService.getSensor(serialNumber, hardwarePort);
        this.sensorAdditionService.delete(sensor.getId());
    }

    @Override
    public String getName() {
        return "DeregisterSensor";
    }
}
