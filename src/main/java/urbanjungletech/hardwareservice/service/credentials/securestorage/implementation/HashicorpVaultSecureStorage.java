package urbanjungletech.hardwareservice.service.credentials.securestorage.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import urbanjungletech.hardwareservice.config.CredentialsVaultProperties;
import urbanjungletech.hardwareservice.exception.SecureStorageRetrievalException;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecretKeyService;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecureStorageService;

import java.util.Collections;

@Service
@ConditionalOnProperty(value = "secure-storage.type", havingValue = "vault")
public class HashicorpVaultSecureStorage implements SecureStorageService {

    private final CredentialsVaultProperties credentialsVaultProperties;
    private final VaultTemplate vaultTemplate;
    private final SecretKeyService secretKeyServiceHash;
    @Autowired
    public HashicorpVaultSecureStorage(VaultTemplate vaultTemplate,
                                       SecretKeyService secretKeyServiceHash,
                                       CredentialsVaultProperties credentialsVaultProperties) {
        this.vaultTemplate = vaultTemplate;
        this.secretKeyServiceHash = secretKeyServiceHash;
        this.credentialsVaultProperties = credentialsVaultProperties;
    }

    @Override
    public String saveSecret(String secret) {
        String key = secretKeyServiceHash.generateSecretKey(secret);
        vaultTemplate.opsForKeyValue(credentialsVaultProperties.getPath() + "/", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .put(key, Collections.singletonMap("value", secret));
        return key;
    }

    @Override
    public void saveSecret(String secretId, String secret) {
        vaultTemplate.write(credentialsVaultProperties.getPath() + "/" + secretId, Collections.singletonMap("value", secret));
    }

    @Override
    public String getSecret(String secretId) {
        VaultResponse response = vaultTemplate.opsForKeyValue(credentialsVaultProperties.getPath() + "/", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .get(secretId);
        if (response != null && response.getData() != null) {
            return (String) response.getData().get("value");
        } else {
            throw new SecureStorageRetrievalException();
        }
    }

    @Override
    public void deleteSecret(String secretId) {
        vaultTemplate.opsForKeyValue(credentialsVaultProperties.getPath() + "/", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .delete(credentialsVaultProperties.getPath() + "/" + secretId);
    }
}
