package urbanjungletech.hardwareservice.converter.credentials;

import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface CredentialsConverter {
    Credentials toModel(CredentialsEntity credentialsEntity);
    void fillEntity(CredentialsEntity entity, Credentials model);

    CredentialsEntity createEntity(Credentials model);
}
