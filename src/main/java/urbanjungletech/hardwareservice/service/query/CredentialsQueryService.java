package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface CredentialsQueryService {
    Credentials findById(Long id);
}
