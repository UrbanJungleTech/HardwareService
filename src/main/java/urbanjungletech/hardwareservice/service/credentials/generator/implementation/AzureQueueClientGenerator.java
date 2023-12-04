package urbanjungletech.hardwareservice.service.credentials.generator.implementation;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.service.credentials.generator.SpecificClientGenerator;

@Service
public class AzureQueueClientGenerator implements SpecificClientGenerator<QueueClient, TokenCredentials> {
    @Override
    public QueueClient generateClient(TokenCredentials credentials) {
        return new QueueClientBuilder()
                .endpoint(credentials.getUrl())
                .sasToken(credentials.getTokenValue())
                .buildClient();
    }
}
