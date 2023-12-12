package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.credentials.DatabaseCredentialsEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.DatabaseSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class DatabaseSensorReadingRouterConverterImpl implements SpecificSensorReadingRouterConverter<DatabaseSensorReadingRouter, DatabaseSensorReadingRouterEntity> {

    private final CredentialsConverter credentialsConverter;
    private final CredentialsDAO credentialsDAO;
    public DatabaseSensorReadingRouterConverterImpl(CredentialsConverter credentialsConverter,
                                                    CredentialsDAO credentialsDAO) {
        this.credentialsConverter = credentialsConverter;
        this.credentialsDAO = credentialsDAO;
    }

    @Override
    public DatabaseSensorReadingRouter toModel(DatabaseSensorReadingRouterEntity entity) {
        DatabaseSensorReadingRouter result = new DatabaseSensorReadingRouter();
        result.setTableName(entity.getTableName());
        result.setValueColumn(entity.getValueColumn());
        result.setTimestampColumn(entity.getTimestampColumn());
        DatabaseCredentials credentials = (DatabaseCredentials) this.credentialsConverter.toModel(entity.getCredentials());
        result.setCredentials(credentials);
        return result;
    }

    @Override
    public DatabaseSensorReadingRouterEntity createEntity(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        DatabaseSensorReadingRouterEntity result = new DatabaseSensorReadingRouterEntity();
        DatabaseCredentialsEntity credentials = (DatabaseCredentialsEntity) this.credentialsDAO.findById(databaseSensorReadingRouter.getCredentials().getId());
        result.setCredentials(credentials);
        return result;
    }

    @Override
    public void fillEntity(DatabaseSensorReadingRouterEntity entity, DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        entity.setTableName(databaseSensorReadingRouter.getTableName());
        entity.setValueColumn(databaseSensorReadingRouter.getValueColumn());
        entity.setTimestampColumn(databaseSensorReadingRouter.getTimestampColumn());
    }
}
