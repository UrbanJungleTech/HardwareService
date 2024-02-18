package urbanjungletech.hardwareservice.service.clientgenerator.implementation;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.service.clientgenerator.AzureQueueClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

@Service
public class AzureQueueClientGeneratorImpl implements AzureQueueClientGenerator {
    private final CredentialsRetrievalService credentialsRetrievalService;

    public AzureQueueClientGeneratorImpl(CredentialsRetrievalService credentialsRetrievalService) {
        this.credentialsRetrievalService = credentialsRetrievalService;
    }
    @Override
    public QueueClient generateClient(AzureConnectionDetails azureConnectionDetails) {
        TokenCredentials credentials = (TokenCredentials) azureConnectionDetails.getCredentials();
        credentials = (TokenCredentials) this.credentialsRetrievalService.getCredentials(credentials);

        return new QueueClientBuilder()
                .sasToken(credentials.getTokenValue())
                .endpoint(azureConnectionDetails.getUrl())
                .buildClient();
    }
}
