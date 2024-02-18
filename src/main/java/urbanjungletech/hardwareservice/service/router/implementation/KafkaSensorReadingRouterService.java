package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;
import urbanjungletech.hardwareservice.service.clientgenerator.KafkaClientGenerator;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class KafkaSensorReadingRouterService implements SpecificSensorReadingRouterService<KafkaSensorReadingRouter> {
    private final KafkaClientGenerator kafkaClientGenerator;

    public KafkaSensorReadingRouterService(KafkaClientGenerator kafkaClientGenerator) {
        this.kafkaClientGenerator = kafkaClientGenerator;
    }
    @Override
    public void route(KafkaSensorReadingRouter routerData, SensorReading sensorReading) {
        KafkaTemplate kafkaTemplate = this.kafkaClientGenerator.generateClient(routerData.getKafkaConnectionDetails());
        kafkaTemplate.send(routerData.getKafkaConnectionDetails().getTopic(), sensorReading);
    }
}
