package urbanjungletech.hardwareservice.service.credentials.retrieval.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.service.credentials.retrieval.SpecificCredentialRetrievalService;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

@Service
public class TokenCredentialsRetrievalService implements SpecificCredentialRetrievalService<TokenCredentials> {

    private final SecureStorageService secureStorageService;

    public TokenCredentialsRetrievalService(SecureStorageService secureStorageService) {
        this.secureStorageService = secureStorageService;
    }
    @Override
    public TokenCredentials getCredentials(TokenCredentials credentials) {
        TokenCredentials result = new TokenCredentials();
        String tokenValue = this.secureStorageService.getSecret(credentials.getTokenValue());
        result.setTokenValue(tokenValue);
        return result;
    }

    @Override
    public TokenCredentials persistCredentials(TokenCredentials credentials) {
        TokenCredentials result = new TokenCredentials();
        String tokenValue = this.secureStorageService.saveSecret(credentials.getTokenValue());
        result.setTokenValue(tokenValue);
        return result;
    }

    @Override
    public void deleteCredentials(TokenCredentials credentials) {
        this.secureStorageService.deleteSecret(credentials.getTokenValue());
    }

    @Override
    public void updateCredentials(TokenCredentials credentialsKeys, TokenCredentials credentialsValues) {
        this.secureStorageService.saveSecret(credentialsKeys.getTokenValue(), credentialsValues.getTokenValue());
    }
}
