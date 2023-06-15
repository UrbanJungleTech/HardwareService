package frentz.daniel.hardwareservice.jsonrpc.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.SensorAdditionService;
import frentz.daniel.hardwareservice.client.model.Sensor;
import org.springframework.stereotype.Service;

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
