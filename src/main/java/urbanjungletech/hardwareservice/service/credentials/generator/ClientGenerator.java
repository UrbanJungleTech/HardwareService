package urbanjungletech.hardwareservice.service.credentials.generator;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface ClientGenerator {
    <ClientType> ClientType generateCredentials(Credentials credentials, Class<ClientType> credentialsType);
}
