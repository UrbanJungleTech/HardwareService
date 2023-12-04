package urbanjungletech.hardwareservice.service.credentials.retrieval.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;
import urbanjungletech.hardwareservice.service.credentials.retrieval.SpecificCredentialRetrievalService;

import java.util.Map;

@Service
public class CredentialsRetrievalServiceImpl implements CredentialsRetrievalService {

    private final Map<Class <? extends Credentials>, SpecificCredentialRetrievalService> specificCredentialRetrievalServiceMap;

    public CredentialsRetrievalServiceImpl(Map<Class <? extends Credentials>, SpecificCredentialRetrievalService> credentialRetrievalServiceMap) {
        this.specificCredentialRetrievalServiceMap = credentialRetrievalServiceMap;
    }

    @Override
    public Credentials getCredentials(Credentials credentials) {
        SpecificCredentialRetrievalService specificCredentialRetrievalService = specificCredentialRetrievalServiceMap.get(credentials.getClass());
        return specificCredentialRetrievalService.getCredentials(credentials);
    }

    @Override
    public Credentials persistCredentials(Credentials credentials) {
        SpecificCredentialRetrievalService specificCredentialRetrievalService = specificCredentialRetrievalServiceMap.get(credentials.getClass());
        return specificCredentialRetrievalService.persistCredentials(credentials);
    }

    @Override
    public void deleteCredentials(Credentials credentials) {
        this.specificCredentialRetrievalServiceMap.get(credentials.getClass()).deleteCredentials(credentials);
    }

    @Override
    public void updateCredentials(Credentials credentialsKeys, Credentials credentialsValues) {
        this.specificCredentialRetrievalServiceMap.get(credentialsKeys.getClass()).updateCredentials(credentialsKeys, credentialsValues);
    }
}
