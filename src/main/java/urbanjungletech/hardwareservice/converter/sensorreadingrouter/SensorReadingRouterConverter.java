package urbanjungletech.hardwareservice.converter.sensorreadingrouter;

import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

public interface SensorReadingRouterConverter {
    SensorReadingRouter toModel(SensorReadingRouterEntity entity);
    void fillEntity(SensorReadingRouterEntity entity, SensorReadingRouter model);

    SensorReadingRouterEntity createEntity(SensorReadingRouter model);
}
