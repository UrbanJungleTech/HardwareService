package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.BasicDatabaseSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.BasicDatabaseSensorReadingRouter;

@Service
public class BasicDatabaseSensorReadingRouterConverter implements
        SpecificSensorReadingRouterConverter<BasicDatabaseSensorReadingRouter, BasicDatabaseSensorReadingRouterEntity> {
    @Override
    public BasicDatabaseSensorReadingRouter toModel(BasicDatabaseSensorReadingRouterEntity entity) {
        BasicDatabaseSensorReadingRouter result = new BasicDatabaseSensorReadingRouter();
        return result;
    }

    @Override
    public BasicDatabaseSensorReadingRouterEntity createEntity(BasicDatabaseSensorReadingRouter basicDatabaseSensorReadingRouter) {
        BasicDatabaseSensorReadingRouterEntity result = new BasicDatabaseSensorReadingRouterEntity();
        return result;
    }

    @Override
    public void fillEntity(BasicDatabaseSensorReadingRouterEntity entity, BasicDatabaseSensorReadingRouter basicDatabaseSensorReadingRouter) {

    }
}
