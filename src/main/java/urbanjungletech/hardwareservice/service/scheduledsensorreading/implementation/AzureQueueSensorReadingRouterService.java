package urbanjungletech.hardwareservice.service.scheduledsensorreading.implementation;

import com.azure.storage.queue.QueueClient;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.service.credentials.generator.implementation.AzureQueueClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;
import urbanjungletech.hardwareservice.service.scheduledsensorreading.SpecificSensorReadingRouterService;

@Service
public class AzureQueueSensorReadingRouterService implements SpecificSensorReadingRouterService<AzureQueueSensorReadingRouter> {

    private final CredentialsRetrievalService credentialsRetrievalService;
    private final AzureQueueClientGenerator azureQueueClientGenerator;

    public AzureQueueSensorReadingRouterService(CredentialsRetrievalService credentialsRetrievalService,
                                                AzureQueueClientGenerator azureQueueClientGenerator) {
        this.credentialsRetrievalService = credentialsRetrievalService;
        this.azureQueueClientGenerator = azureQueueClientGenerator;
    }

    @Override
    public void route(AzureQueueSensorReadingRouter routerData, SensorReading sensorReading) {
        TokenCredentials credentials = (TokenCredentials) this.credentialsRetrievalService.getCredentials(routerData.getCredentials());
        QueueClient queueClient = this.azureQueueClientGenerator.generateClient(credentials);
        //queueClient.sendMessage(sensorReading.getReading());
    }
}
