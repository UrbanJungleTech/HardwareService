package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.Map;

@Service
public class SensorReadingRouterConverterImpl implements SensorReadingRouterConverter {

    private final Map<Class, SpecificSensorReadingRouterConverter> converters;

    public SensorReadingRouterConverterImpl(Map<Class, SpecificSensorReadingRouterConverter> converters) {
        this.converters = converters;
    }

    @Override
    public SensorReadingRouter toModel(SensorReadingRouterEntity entity) {
        SensorReadingRouter result = this.converters.get(entity.getClass()).toModel(entity);
        result.setId(entity.getId());
        result.setScheduledSensorReadingId(entity.getScheduledSensorReadingEntity().getId());
        return result;
    }

    @Override
    public void fillEntity(SensorReadingRouterEntity entity, SensorReadingRouter model) {
        this.converters.get(model.getClass()).fillEntity(entity, model);
        entity.setType(model.getType());
    }

    @Override
    public SensorReadingRouterEntity createEntity(SensorReadingRouter model) {
        SensorReadingRouterEntity result = this.converters.get(model.getClass()).createEntity(model);
        this.fillEntity(result, model);
        return result;
    }
}
