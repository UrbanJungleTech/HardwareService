package urbanjungletech.hardwareservice.service.credentials.generator.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.service.credentials.generator.ClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.generator.SpecificClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

import java.util.Map;

@Service
public class ClientGeneratorImpl implements ClientGenerator {
    private final CredentialsRetrievalService credentialsRetrievalService;
    private final Map<Class, SpecificClientGenerator> specificCredentialsGenerators;
    public ClientGeneratorImpl(CredentialsRetrievalService credentialsRetrievalService,
                               Map<Class, SpecificClientGenerator> specificCredentialsGenerators) {
        this.credentialsRetrievalService = credentialsRetrievalService;
        this.specificCredentialsGenerators = specificCredentialsGenerators;
    }

    @Override
    public <ClientType> ClientType generateCredentials(Credentials credentials, Class<ClientType> credentialsType) {
        Credentials retrievedCredentials = this.credentialsRetrievalService.getCredentials(credentials);
        SpecificClientGenerator specificClientGenerator = specificCredentialsGenerators.get(credentialsType);
        return (ClientType)specificClientGenerator.generateClient(retrievedCredentials);
    }
}
