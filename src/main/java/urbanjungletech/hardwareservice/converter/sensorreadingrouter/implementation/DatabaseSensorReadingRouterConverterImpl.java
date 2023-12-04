package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.DatabaseSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

@Service
public class DatabaseSensorReadingRouterConverterImpl implements SpecificSensorReadingRouterConverter<DatabaseSensorReadingRouter, DatabaseSensorReadingRouterEntity> {
    @Override
    public DatabaseSensorReadingRouter toModel(DatabaseSensorReadingRouterEntity entity) {
        DatabaseSensorReadingRouter result = new DatabaseSensorReadingRouter();
        result.setTableName(entity.getTableName());
        result.setValueColumn(entity.getValueColumn());
        result.setTimestampColumn(entity.getTimestampColumn());
        return result;
    }

    @Override
    public DatabaseSensorReadingRouterEntity createEntity(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        DatabaseSensorReadingRouterEntity result = new DatabaseSensorReadingRouterEntity();
        result.setTableName(databaseSensorReadingRouter.getTableName());
        result.setValueColumn(databaseSensorReadingRouter.getValueColumn());
        result.setTimestampColumn(databaseSensorReadingRouter.getTimestampColumn());
        return result;
    }

    @Override
    public void fillEntity(DatabaseSensorReadingRouterEntity entity, DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        entity.setTableName(databaseSensorReadingRouter.getTableName());
        entity.setValueColumn(databaseSensorReadingRouter.getValueColumn());
        entity.setTimestampColumn(databaseSensorReadingRouter.getTimestampColumn());
    }
}
