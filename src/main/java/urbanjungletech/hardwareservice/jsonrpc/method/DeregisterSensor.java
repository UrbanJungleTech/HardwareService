package urbanjungletech.hardwareservice.jsonrpc.method;

import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.SensorService;
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
        String sensorPort = (String)params.get("port");
        Sensor sensor = this.sensorService.getSensor(serialNumber, sensorPort);
        this.sensorAdditionService.delete(sensor.getId());
    }
}
