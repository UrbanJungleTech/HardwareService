package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;

@Service
public class AzureQueueSensorRoutingAdditionService implements SpecificAdditionService<AzureQueueSensorReadingRouter> {

    private final CredentialsAdditionService credentialsAdditionService;

    public AzureQueueSensorRoutingAdditionService(CredentialsAdditionService credentialsAdditionService){
        this.credentialsAdditionService = credentialsAdditionService;
    }

    @Override
    public void create(AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        Credentials credentials = this.credentialsAdditionService.create(azureQueueSensorReadingRouter.getCredentials());
        azureQueueSensorReadingRouter.setCredentials(credentials);
    }

    @Override
    public void delete(long id) {
        this.credentialsAdditionService.delete(id);
    }

    @Override
    public void update(long id, AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        Credentials credentials = this.credentialsAdditionService.update(azureQueueSensorReadingRouter.getId(), azureQueueSensorReadingRouter.getCredentials());
        azureQueueSensorReadingRouter.setCredentials(credentials);
    }
}
