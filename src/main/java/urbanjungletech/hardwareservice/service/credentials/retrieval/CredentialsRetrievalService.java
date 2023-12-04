package urbanjungletech.hardwareservice.service.credentials.retrieval;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public interface CredentialsRetrievalService {
    /**
     * Retrieves the actual credentials from secure storage based on the provided reference credentials.
     * The input credentials object contains key references (not actual credential values) that map to the real credentials stored in secure storage.
     * This method uses these key references to fetch and return the actual credentials with their real values.
     * Note: The returned credentials object is distinct from the input object and contains the actual credential values.
     *
     * @param credentials The reference credentials with keys for secure storage lookup.
     * @return The actual credentials with real values retrieved from secure storage.
     */

    Credentials getCredentials(Credentials credentials);

    Credentials persistCredentials(Credentials credentials);
    void deleteCredentials(Credentials credentials);
    void updateCredentials(Credentials credentialsKeys, Credentials credentialsValues);
}
