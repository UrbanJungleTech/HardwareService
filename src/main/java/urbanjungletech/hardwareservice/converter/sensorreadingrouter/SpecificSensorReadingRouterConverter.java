package urbanjungletech.hardwareservice.converter.sensorreadingrouter;

import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

public interface SpecificSensorReadingRouterConverter<Model extends SensorReadingRouter,
        Entity extends SensorReadingRouterEntity> {
    Model toModel(Entity entity);
    Entity createEntity(Model model);
    void fillEntity(Entity entity, Model model);
}
