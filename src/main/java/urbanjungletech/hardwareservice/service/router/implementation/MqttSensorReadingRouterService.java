package urbanjungletech.hardwareservice.service.router.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.RouterSerializationException;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.MqttSensorReadingRouter;
import urbanjungletech.hardwareservice.service.client.generator.implementation.MqttClientGenerator;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class MqttSensorReadingRouterService implements SpecificSensorReadingRouterService<MqttSensorReadingRouter> {
    private final MqttClientGenerator mqttClientGenerator;
    private final ObjectMapper objectMapper;
    public MqttSensorReadingRouterService(MqttClientGenerator mqttClientGenerator,
                                          ObjectMapper objectMapper) {
        this.mqttClientGenerator = mqttClientGenerator;
        this.objectMapper = objectMapper;
    }
    @Override
    public void route(MqttSensorReadingRouter routerData, SensorReading sensorReading) {
        try {
            MqttClient client = this.mqttClientGenerator.generateClient(routerData.getConnectionDetails());
            MqttMessage message = new MqttMessage();
            String payload = this.objectMapper.writeValueAsString(sensorReading);
            message.setPayload(payload.getBytes());
            client.publish(routerData.getTopic(), message);
        } catch (Exception e) {
            throw new RouterSerializationException(e);
        }
    }
}
