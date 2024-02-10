package urbanjungletech.hardwareservice.service.client.generator.implementation;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.service.client.generator.SpecificClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

@Service
public class AzureQueueClientGenerator implements SpecificClientGenerator<QueueClient, AzureConnectionDetails> {
    private CredentialsRetrievalService credentialsRetrievalService;

    public AzureQueueClientGenerator(CredentialsRetrievalService credentialsRetrievalService) {
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
