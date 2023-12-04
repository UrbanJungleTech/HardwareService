package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.KafkaSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;

@Service
public class KafkaSensorReadingRouterConverter implements SpecificSensorReadingRouterConverter<KafkaSensorReadingRouter, KafkaSensorReadingRouterEntity> {
    @Override
    public KafkaSensorReadingRouter toModel(KafkaSensorReadingRouterEntity entity) {
        KafkaSensorReadingRouter result = new KafkaSensorReadingRouter();
        return result;
    }

    @Override
    public KafkaSensorReadingRouterEntity createEntity(KafkaSensorReadingRouter kafkaSensorReadingRouter) {
        KafkaSensorReadingRouterEntity result = new KafkaSensorReadingRouterEntity();
        this.fillEntity(result, kafkaSensorReadingRouter);
        return result;
    }

    @Override
    public void fillEntity(KafkaSensorReadingRouterEntity entity, KafkaSensorReadingRouter kafkaSensorReadingRouter) {

    }
}
