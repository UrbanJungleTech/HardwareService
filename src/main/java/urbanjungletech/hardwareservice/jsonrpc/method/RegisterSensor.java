package urbanjungletech.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.Map;

@Service
public class RegisterSensor implements RpcMethod{

    private ObjectMapper objectMapper;
    private SensorAdditionService sensorAdditionService;

    public RegisterSensor(ObjectMapper objectMapper, SensorAdditionService sensorAdditionService){
        this.objectMapper = objectMapper;
        this.sensorAdditionService = sensorAdditionService;
    }

    @Override
    public void process(Map<String, Object> params) {
        Sensor sensor = this.objectMapper.convertValue(params.get("sensor"), Sensor.class);
        this.sensorAdditionService.create(sensor);
    }
}
