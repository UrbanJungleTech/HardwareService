package urbanjungletech.hardwareservice.service.credentials.retrieval.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;
import urbanjungletech.hardwareservice.service.credentials.retrieval.SpecificCredentialRetrievalService;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

@Service
public class UsernamePasswordCredentialsRetrievalService implements SpecificCredentialRetrievalService<UsernamePasswordCredentials> {

    private final SecureStorageService secureStorageService;

    public UsernamePasswordCredentialsRetrievalService(SecureStorageService secureStorageService) {
        this.secureStorageService = secureStorageService;
    }

    @Override
    public UsernamePasswordCredentials getCredentials(UsernamePasswordCredentials credentials) {
        UsernamePasswordCredentials result = new UsernamePasswordCredentials();
        String password = this.secureStorageService.getSecret(credentials.getPassword());
        result.setPassword(password);
        String username = this.secureStorageService.getSecret(credentials.getUsername());
        result.setUsername(username);
        return result;
    }

    @Override
    public UsernamePasswordCredentials persistCredentials(UsernamePasswordCredentials credentials) {
        UsernamePasswordCredentials result = new UsernamePasswordCredentials();
        String username = this.secureStorageService.saveSecret(credentials.getUsername());
        result.setUsername(username);
        String password = this.secureStorageService.saveSecret(credentials.getPassword());
        result.setPassword(password);
        return result;
    }

    @Override
    public void deleteCredentials(UsernamePasswordCredentials credentials) {
        this.secureStorageService.deleteSecret(credentials.getUsername());
        this.secureStorageService.deleteSecret(credentials.getPassword());
    }

    @Override
    public void updateCredentials(UsernamePasswordCredentials credentialsKeys, UsernamePasswordCredentials credentialsValues) {
        this.secureStorageService.saveSecret(credentialsKeys.getUsername(), credentialsValues.getUsername());
        this.secureStorageService.saveSecret(credentialsKeys.getPassword(), credentialsValues.getPassword());
    }

}
