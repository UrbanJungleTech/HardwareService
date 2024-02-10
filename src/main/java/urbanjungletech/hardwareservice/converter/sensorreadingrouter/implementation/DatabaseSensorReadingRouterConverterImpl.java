package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.implementation.SpecificDatabaseConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.connectiondetails.DatabaseConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.DatabaseCredentialsEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.DatabaseSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.repository.ConnectionDetailsRepository;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class DatabaseSensorReadingRouterConverterImpl implements SpecificSensorReadingRouterConverter<DatabaseSensorReadingRouter, DatabaseSensorReadingRouterEntity> {

    private final SpecificDatabaseConnectionDetailsConverter databaseConnectionDetailsConverter;
    private final ConnectionDetailsRepository connectionDetailsRepository;
    private final SpecificDatabaseConnectionDetailsConverter specificDatabaseConnectionDetailsConverter;

    public DatabaseSensorReadingRouterConverterImpl(SpecificDatabaseConnectionDetailsConverter databaseConnectionDetailsConverter,
                                                    ConnectionDetailsRepository connectionDetailsRepository,
                                                    SpecificDatabaseConnectionDetailsConverter specificDatabaseConnectionDetailsConverter) {
        this.databaseConnectionDetailsConverter = databaseConnectionDetailsConverter;
        this.connectionDetailsRepository = connectionDetailsRepository;
        this.specificDatabaseConnectionDetailsConverter = specificDatabaseConnectionDetailsConverter;
    }

    @Override
    public DatabaseSensorReadingRouter toModel(DatabaseSensorReadingRouterEntity entity) {
        DatabaseSensorReadingRouter result = new DatabaseSensorReadingRouter();
        result.setTableName(entity.getTableName());
        result.setValueColumn(entity.getValueColumn());
        result.setTimestampColumn(entity.getTimestampColumn());
        result.setDatabaseConnectionDetails(this.databaseConnectionDetailsConverter.toModel(entity.getConnectionDetails()));
        return result;
    }

    @Override
    public DatabaseSensorReadingRouterEntity createEntity(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        DatabaseSensorReadingRouterEntity result = new DatabaseSensorReadingRouterEntity();
        this.fillEntity(result, databaseSensorReadingRouter);
        return result;
    }

    @Override
    public void fillEntity(DatabaseSensorReadingRouterEntity entity, DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        entity.setTableName(databaseSensorReadingRouter.getTableName());
        entity.setValueColumn(databaseSensorReadingRouter.getValueColumn());
        entity.setTimestampColumn(databaseSensorReadingRouter.getTimestampColumn());
        DatabaseConnectionDetailsEntity connectionDetailsEntity = (DatabaseConnectionDetailsEntity)this.connectionDetailsRepository.findById(databaseSensorReadingRouter.getDatabaseConnectionDetails().getId())
                .orElseThrow(() -> new RuntimeException("Connection details not found"));
        entity.setConnectionDetails(connectionDetailsEntity);
    }
}
