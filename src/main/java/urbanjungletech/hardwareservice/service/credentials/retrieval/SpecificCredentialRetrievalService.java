package urbanjungletech.hardwareservice.service.credentials.retrieval;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface SpecificCredentialRetrievalService <T extends Credentials>{
    T getCredentials(T credentials);
    T persistCredentials(T credentials);

    void deleteCredentials(T credentials);

    void updateCredentials(T credentialsKeys, T credentialsValues);
}
