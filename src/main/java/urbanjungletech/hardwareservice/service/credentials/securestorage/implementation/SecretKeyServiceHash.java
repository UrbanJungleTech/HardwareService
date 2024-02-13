package urbanjungletech.hardwareservice.service.credentials.securestorage.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.credentials.securestorage.SecretKeyService;

import java.util.UUID;

@Service
public class SecretKeyServiceHash implements SecretKeyService {
    @Override
    public String generateSecretKey(String key) {
        return UUID.randomUUID().toString();
    }
}
