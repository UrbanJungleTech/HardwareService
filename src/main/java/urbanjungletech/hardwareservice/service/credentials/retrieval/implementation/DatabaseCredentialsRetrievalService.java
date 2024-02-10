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
        return result;
    }

    @Override
    public DatabaseCredentials persistCredentials(DatabaseCredentials credentials) {
        DatabaseCredentials result = new DatabaseCredentials();
        String username = this.secureStorageService.saveSecret(credentials.getUsername());
        result.setUsername(username);
        String password = this.secureStorageService.saveSecret(credentials.getPassword());
        result.setPassword(password);
        return result;
    }

    @Override
    public void deleteCredentials(DatabaseCredentials credentials) {
        this.secureStorageService.deleteSecret(credentials.getUsername());
        this.secureStorageService.deleteSecret(credentials.getPassword());
    }

    @Override
    public void updateCredentials(DatabaseCredentials credentialsKeys, DatabaseCredentials credentialsValues) {
        this.secureStorageService.saveSecret(credentialsKeys.getUsername(), credentialsValues.getUsername());
        this.secureStorageService.saveSecret(credentialsKeys.getPassword(), credentialsValues.getPassword());
    }
}
