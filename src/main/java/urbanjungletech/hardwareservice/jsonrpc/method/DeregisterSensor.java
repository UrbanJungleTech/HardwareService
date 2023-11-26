package urbanjungletech.hardwareservice.jsonrpc.method;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;

import java.util.Map;

@Service
public class DeregisterSensor implements RpcMethod{

    private SensorQueryService sensorQueryService;
    private SensorAdditionService sensorAdditionService;

    public DeregisterSensor(SensorQueryService sensorQueryService,
                            SensorAdditionService sensorAdditionService){
        this.sensorQueryService = sensorQueryService;
        this.sensorAdditionService = sensorAdditionService;
    }

    @Override
    public void process(Map<String, Object> params) {
        String serialNumber = (String)params.get("serialNumber");
        String sensorPort = (String)params.get("port");
        Sensor sensor = this.sensorQueryService.getSensor(serialNumber, sensorPort);
        this.sensorAdditionService.delete(sensor.getId());
    }
}
