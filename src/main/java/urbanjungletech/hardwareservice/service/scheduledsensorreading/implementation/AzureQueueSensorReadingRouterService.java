package urbanjungletech.hardwareservice.service.scheduledsensorreading.implementation;

import com.azure.storage.queue.QueueClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.RouterSerializationException;
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
    private final ObjectMapper objectMapper;

    public AzureQueueSensorReadingRouterService(CredentialsRetrievalService credentialsRetrievalService,
                                                AzureQueueClientGenerator azureQueueClientGenerator,
                                                ObjectMapper objectMapper) {
        this.credentialsRetrievalService = credentialsRetrievalService;
        this.azureQueueClientGenerator = azureQueueClientGenerator;
        this.objectMapper = objectMapper;
    }

    @Override
    public void route(AzureQueueSensorReadingRouter routerData, SensorReading sensorReading) {
        String sensorReadingJson;
        try {
            sensorReadingJson = this.objectMapper.writeValueAsString(sensorReading);
        } catch (Exception e) {
            throw new RouterSerializationException(e);
        }
        TokenCredentials credentials = (TokenCredentials) this.credentialsRetrievalService.getCredentials(routerData.getCredentials());
        QueueClient queueClient = this.azureQueueClientGenerator.generateClient(credentials);
    }
}
