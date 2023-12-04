package urbanjungletech.hardwareservice.service.credentials.securestorage.implementation;

import com.azure.security.keyvault.secrets.SecretClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

import java.util.UUID;

@Service
@ConditionalOnProperty(value = "secure-storage.type", havingValue = "azure")
public class AzureSecureStorage implements SecureStorageService {

    private final SecretClient secretClient;

    public AzureSecureStorage(SecretClient secretClient) {
        this.secretClient = secretClient;
    }
    @Override
    public String saveSecret(String secret) {
        String uuid = UUID.randomUUID().toString();
        this.secretClient.setSecret(uuid, secret);
        return uuid;
    }

    @Override
    public void saveSecret(String secretId, String secret) {
        this.secretClient.setSecret(secretId, secret);
    }

    @Override
    public String getSecret(String secretId) {
        return this.secretClient.getSecret(secretId).getValue();
    }

    @Override
    public void deleteSecret(String secretId) {
        this.secretClient.beginDeleteSecret(secretId);
    }
}
