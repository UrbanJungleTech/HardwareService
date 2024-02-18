package urbanjungletech.hardwareservice.service.router.implementation;

import com.azure.storage.queue.QueueClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.RouterSerializationException;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.service.clientgenerator.AzureQueueClientGenerator;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class AzureQueueSensorReadingRouterService implements SpecificSensorReadingRouterService<AzureQueueSensorReadingRouter> {

    private final AzureQueueClientGenerator azureQueueClientGenerator;
    private final ObjectMapper objectMapper;

    public AzureQueueSensorReadingRouterService(AzureQueueClientGenerator azureQueueClientGenerator,
                                                ObjectMapper objectMapper) {
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
        QueueClient queueClient = this.azureQueueClientGenerator.generateClient(routerData.getAzureConnectionDetails());
        queueClient.sendMessage(sensorReadingJson);
    }
}
