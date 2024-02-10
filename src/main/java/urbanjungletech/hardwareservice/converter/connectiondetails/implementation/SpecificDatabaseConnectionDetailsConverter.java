package urbanjungletech.hardwareservice.converter.connectiondetails.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.DatabaseConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.repository.CredentialsRepository;

@Service
public class SpecificDatabaseConnectionDetailsConverter implements SpecificConnectionDetailsConverter<DatabaseConnectionDetails, DatabaseConnectionDetailsEntity> {

    private final CredentialsConverter credentialsConverter;
    private final CredentialsRepository credentialsRepository;

    public SpecificDatabaseConnectionDetailsConverter(CredentialsConverter credentialsConverter,
                                                      CredentialsRepository credentialsRepository) {
        this.credentialsConverter = credentialsConverter;
        this.credentialsRepository = credentialsRepository;
    }
    @Override
    public DatabaseConnectionDetails toModel(DatabaseConnectionDetailsEntity entity) {
        DatabaseConnectionDetails result = new DatabaseConnectionDetails();
        Credentials credentials = this.credentialsConverter.toModel(entity.getCredentials());
        result.setCredentials(credentials);
        result.setUrl(entity.getUrl());
        result.setPort(entity.getPort());
        result.setDatabase(entity.getDatabase());
        return result;
    }

    @Override
    public void fillEntity(DatabaseConnectionDetailsEntity entity, DatabaseConnectionDetails connectionDetails) {
        entity.setUrl(connectionDetails.getUrl());
        entity.setDatabase(connectionDetails.getDatabase());
        entity.setPort(connectionDetails.getPort());
        CredentialsEntity credentialsEntity = this.credentialsRepository.findById(connectionDetails.getCredentials().getId())
                .orElseThrow(() -> new RuntimeException("Credentials not found"));
        entity.setCredentials(credentialsEntity);
    }

    @Override
    public DatabaseConnectionDetailsEntity createEntity(DatabaseConnectionDetails connectionDetails) {
        DatabaseConnectionDetailsEntity result = new DatabaseConnectionDetailsEntity();
        return result;
    }
}
