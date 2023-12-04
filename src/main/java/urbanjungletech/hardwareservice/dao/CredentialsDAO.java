package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface CredentialsDAO {
    CredentialsEntity createCredentials(Credentials credentials);
    CredentialsEntity findById(Long id);

}
