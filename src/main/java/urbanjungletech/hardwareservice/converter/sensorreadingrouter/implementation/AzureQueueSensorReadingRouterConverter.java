package urbanjungletech.hardwareservice.converter.sensorreadingrouter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.AzureQueueSensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;
import urbanjungletech.hardwareservice.service.query.CredentialsQueryService;

@Service
public class AzureQueueSensorReadingRouterConverter implements urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter<urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter, urbanjungletech.hardwareservice.entity.sensorreadingrouter.AzureQueueSensorReadingRouterEntity> {

    private final CredentialsConverter credentialsConverter;
    private final CredentialsDAO credentialsDAO;

    public AzureQueueSensorReadingRouterConverter(CredentialsConverter credentialsConverter,
                                                  CredentialsDAO credentialsDAO) {
        this.credentialsConverter = credentialsConverter;
        this.credentialsDAO = credentialsDAO;
    }

    public AzureQueueSensorReadingRouter toModel(urbanjungletech.hardwareservice.entity.sensorreadingrouter.AzureQueueSensorReadingRouterEntity entity) {
        AzureQueueSensorReadingRouter result = new AzureQueueSensorReadingRouter();
        result.setQueueName(entity.getQueueName());
        Credentials credentials = this.credentialsConverter.toModel(entity.getCredentials());
        result.setCredentials(credentials);
        return result;
    }

    public AzureQueueSensorReadingRouterEntity createEntity(AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        AzureQueueSensorReadingRouterEntity result = new AzureQueueSensorReadingRouterEntity();
        CredentialsEntity credentialsEntity = this.credentialsDAO.findById(azureQueueSensorReadingRouter.getCredentials().getId());
        result.setCredentials(credentialsEntity);
        return result;
    }

    public void fillEntity(AzureQueueSensorReadingRouterEntity entity, AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        entity.setQueueName(azureQueueSensorReadingRouter.getQueueName());
    }
}
