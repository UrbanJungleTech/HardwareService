package urbanjungletech.hardwareservice.converter.credentials.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.entity.credentials.DatabaseCredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

@Service
public class DatabaseCredentialsConverter implements SpecificCredentialsConverter<DatabaseCredentials, DatabaseCredentialsEntity>{
    @Override
    public DatabaseCredentials toModel(DatabaseCredentialsEntity entity) {
        DatabaseCredentials result = new DatabaseCredentials();
        result.setHost(entity.getHost());
        result.setPort(entity.getPort());
        result.setDatabase(entity.getDatabase());
        result.setUsername(entity.getUsername());
        result.setPassword(entity.getPassword());
        result.setDialect(entity.getDialect());
        result.setDriver(entity.getDriver());
        return result;
    }

    @Override
    public DatabaseCredentialsEntity createEntity(DatabaseCredentials databaseCredentials) {
        DatabaseCredentialsEntity result = new DatabaseCredentialsEntity();
        this.fillEntity(result, databaseCredentials);
        return result;
    }

    @Override
    public void fillEntity(DatabaseCredentialsEntity entity, DatabaseCredentials databaseCredentials) {
        entity.setHost(databaseCredentials.getHost());
        entity.setPort(databaseCredentials.getPort());
        entity.setDatabase(databaseCredentials.getDatabase());
        entity.setUsername(databaseCredentials.getUsername());
        entity.setPassword(databaseCredentials.getPassword());
        entity.setDialect(databaseCredentials.getDialect());
        entity.setDriver(databaseCredentials.getDriver());
    }
}
