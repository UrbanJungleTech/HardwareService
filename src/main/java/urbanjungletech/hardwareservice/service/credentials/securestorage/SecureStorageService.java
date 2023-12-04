package urbanjungletech.hardwareservice.service.credentials.securestorage;

public interface SecureStorageService {
    String saveSecret(String secret);
    void saveSecret(String secretId, String secret);
    String getSecret(String secretId);
    void deleteSecret(String secretId);
}
