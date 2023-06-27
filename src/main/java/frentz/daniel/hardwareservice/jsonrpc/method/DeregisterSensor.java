package frentz.daniel.hardwareservice.jsonrpc.method;

import frentz.daniel.hardwareservice.addition.SensorAdditionService;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.service.SensorService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
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
        int hardwarePort = (int)params.get("port");
        Sensor sensor = this.sensorService.getSensor(serialNumber, hardwarePort);
        this.sensorAdditionService.delete(sensor.getId());
    }
}
