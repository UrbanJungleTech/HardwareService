package urbanjungletech.hardwareservice.service.credentials.generator;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface SpecificClientGenerator<ClientType, CredentialsType extends Credentials> {
    ClientType generateClient(CredentialsType credentials);
}
