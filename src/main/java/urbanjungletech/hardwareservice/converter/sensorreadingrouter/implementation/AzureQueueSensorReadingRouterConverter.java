package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.AzureConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.AzureQueueSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.repository.ConnectionDetailsRepository;

@Service
public class AzureQueueSensorReadingRouterConverter implements urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter<urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter, urbanjungletech.hardwareservice.entity.sensorreadingrouter.AzureQueueSensorReadingRouterEntity> {

    private final ConnectionDetailsConverter specificAzureConnectionDetailsConverter;
    private final ConnectionDetailsRepository connectionDetailsRepository;
    public AzureQueueSensorReadingRouterConverter(ConnectionDetailsConverter specificAzureConnectionDetailsConverter,
                                                  ConnectionDetailsRepository connectionDetailsRepository) {
        this.specificAzureConnectionDetailsConverter = specificAzureConnectionDetailsConverter;
        this.connectionDetailsRepository = connectionDetailsRepository;
    }


    public AzureQueueSensorReadingRouter toModel(AzureQueueSensorReadingRouterEntity entity) {
        AzureQueueSensorReadingRouter result = new AzureQueueSensorReadingRouter();
        AzureConnectionDetails azureConnectionDetails = (AzureConnectionDetails) this.specificAzureConnectionDetailsConverter.toModel(entity.getAzureConnectionDetails());
        result.setAzureConnectionDetails(azureConnectionDetails);
        result.setQueueName(entity.getQueueName());
        result.setId(entity.getId());
        return result;
    }

    public AzureQueueSensorReadingRouterEntity createEntity(AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        AzureQueueSensorReadingRouterEntity result = new AzureQueueSensorReadingRouterEntity();
        this.fillEntity(result, azureQueueSensorReadingRouter);
        return result;
    }

    public void fillEntity(AzureQueueSensorReadingRouterEntity entity, AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        AzureConnectionDetailsEntity azureConnectionDetailsEntity = (AzureConnectionDetailsEntity)this.connectionDetailsRepository.findById(azureQueueSensorReadingRouter.getAzureConnectionDetails().getId())
                .orElseThrow(() -> new RuntimeException("Connection details not found"));
        entity.setAzureConnectionDetails(azureConnectionDetailsEntity);
        entity.setQueueName(azureQueueSensorReadingRouter.getQueueName());
    }
}
