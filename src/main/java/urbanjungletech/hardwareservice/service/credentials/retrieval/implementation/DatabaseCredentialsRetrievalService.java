package urbanjungletech.hardwareservice.service.credentials.retrieval.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.service.credentials.retrieval.SpecificCredentialRetrievalService;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

@Service
public class DatabaseCredentialsRetrievalService implements SpecificCredentialRetrievalService<DatabaseCredentials> {

    private final SecureStorageService secureStorageService;

    public DatabaseCredentialsRetrievalService(SecureStorageService secureStorageService) {
        this.secureStorageService = secureStorageService;
    }

    @Override
    public DatabaseCredentials getCredentials(DatabaseCredentials credentials) {
        DatabaseCredentials result = new DatabaseCredentials();
        String username = this.secureStorageService.getSecret(credentials.getUsername());
        result.setUsername(username);
        String password = this.secureStorageService.getSecret(credentials.getPassword());
        result.setPassword(password);
        String host = this.secureStorageService.getSecret(credentials.getHost());
        result.setHost(host);
        String port = this.secureStorageService.getSecret(credentials.getPort());
        result.setPort(port);
        String database = this.secureStorageService.getSecret(credentials.getDatabase());
        result.setDatabase(database);
        String driver = this.secureStorageService.getSecret(credentials.getDriver());
        result.setDriver(driver);
        String dialect = this.secureStorageService.getSecret(credentials.getDialect());
        result.setDialect(dialect);
        return result;
    }

    @Override
    public DatabaseCredentials persistCredentials(DatabaseCredentials credentials) {
        DatabaseCredentials result = new DatabaseCredentials();
        String username = this.secureStorageService.saveSecret(credentials.getUsername());
        result.setUsername(username);
        String password = this.secureStorageService.saveSecret(credentials.getPassword());
        result.setPassword(password);
        String host = this.secureStorageService.saveSecret(credentials.getHost());
        result.setHost(host);
        String port = this.secureStorageService.saveSecret(credentials.getPort());
        result.setPort(port);
        String database = this.secureStorageService.saveSecret(credentials.getDatabase());
        result.setDatabase(database);
        String driver = this.secureStorageService.saveSecret(credentials.getDriver());
        result.setDriver(driver);
        String dialect = this.secureStorageService.saveSecret(credentials.getDialect());
        result.setDialect(dialect);
        return result;
    }

    @Override
    public void deleteCredentials(DatabaseCredentials credentials) {
        this.secureStorageService.deleteSecret(credentials.getUsername());
        this.secureStorageService.deleteSecret(credentials.getPassword());
        this.secureStorageService.deleteSecret(credentials.getHost());
        this.secureStorageService.deleteSecret(credentials.getPort());
        this.secureStorageService.deleteSecret(credentials.getDatabase());
        this.secureStorageService.deleteSecret(credentials.getDriver());
    }

    @Override
    public void updateCredentials(DatabaseCredentials credentialsKeys, DatabaseCredentials credentialsValues) {
        this.secureStorageService.saveSecret(credentialsKeys.getUsername(), credentialsValues.getUsername());
        this.secureStorageService.saveSecret(credentialsKeys.getPassword(), credentialsValues.getPassword());
        this.secureStorageService.saveSecret(credentialsKeys.getHost(), credentialsValues.getHost());
        this.secureStorageService.saveSecret(credentialsKeys.getPort(), credentialsValues.getPort());
        this.secureStorageService.saveSecret(credentialsKeys.getDatabase(), credentialsValues.getDatabase());
        this.secureStorageService.saveSecret(credentialsKeys.getDriver(), credentialsValues.getDriver());
    }
}
